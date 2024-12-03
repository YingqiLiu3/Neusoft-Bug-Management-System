package com.dlut.bugtestmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.constants.SystemConstants;
import com.dlut.bugtestmanage.dto.UserInfo;
import com.dlut.bugtestmanage.entity.*;
import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;
import com.dlut.bugtestmanage.exception.SystemException;
import com.dlut.bugtestmanage.mapper.*;
import com.dlut.bugtestmanage.service.ProjectService;
import com.dlut.bugtestmanage.utils.BeanCopyUtils;
import com.dlut.bugtestmanage.utils.UserThreadLocalUtil;
import com.dlut.bugtestmanage.vo.PageVo;
import com.dlut.bugtestmanage.vo.ProjectStatusVo;
import com.dlut.bugtestmanage.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Project)表服务实现类
 */
@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserProjectMapper userProjectMapper;

    @Autowired
    private BugMapper bugMapper;

    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    @Autowired
    private  TestCaseMapper testCaseMapper;

    @Transactional
    @Override
    public ResponseResult create(Project project) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(project.getProjectName())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_NAME_NOT_NULL);
        }
        if (!StringUtils.hasText(project.getProjectDescription())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_DESCRIPTION_NOT_NULL);
        }
        if (!StringUtils.hasText(project.getProjectCreatedBy())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_CREATOR_NOT_NULL);
        }
        if (!StringUtils.hasText(project.getProjectCreatedTime())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_CREATE_TIME_NOT_NULL);
        }
        // 对创建人进行合法性判断
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, project.getProjectCreatedBy());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_CREATOR_NOT_EXIST);
        } else if (!Objects.equals(user.getRoleId(), SystemConstants.PRODUCT_MANAGER)) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_CREATOR_AUTH_ERROR);
        }

        // 保存项目
        save(project);

        // 建立项目创建人与项目之间的关联
        UserProject userProject = new UserProject();
        userProject.setUserId(user.getUserId());
        userProject.setProjectId(project.getProjectId());
        userProjectMapper.insert(userProject);

        return ResponseResult.okResult(project);
    }

    // 根据当前登录用户搜索其参与的项目
    @Override
    public ResponseResult getProjects(String query) {
        // 获取用户信息(username role)
        UserInfo userInfo = UserThreadLocalUtil.getUser();

        // 根据username查到该用户
        String username = userInfo.getUsername();
        String role = userInfo.getRole();

        // query为空返回用户所属所有项目
        if (query == null || query.equals("")) {
            List<Project> list = projectMapper.findProjectsListByUserName(username, role);

            return ResponseResult.okResult(list);
        } else {
            // query不为空搜索该项目
            List<Project> projectsListByQuery = projectMapper.findProjectsListByQuery(username, role, query);

            return ResponseResult.okResult(projectsListByQuery);
        }

    }

    @Override
    public ResponseResult getProjectDetail(Integer projectId) {
        if(!projectExist(projectId)) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_NOT_EXISTS);
        }
        Project project = projectMapper.selectById(projectId);
        return ResponseResult.okResult(project);
    }

    @Override
    public ResponseResult submitProjectInfo(Project project) {
        if(!projectExist(project.getProjectId())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_NOT_EXISTS);
        }
        Project selectById = projectMapper.selectById(project.getProjectId());
        if(StringUtils.hasText(project.getProjectCreatedBy())) {
            if(!selectById.getProjectCreatedBy().equals(project.getProjectCreatedBy())) {
                throw new SystemException(AppHttpCodeEnum.PROJECT_CREATOR_CANNOT_MODIFY);
            }
        }
        if(StringUtils.hasText(project.getProjectCreatedTime())) {
            if(!selectById.getProjectCreatedTime().equals(project.getProjectCreatedTime())) {
                throw new SystemException(AppHttpCodeEnum.PROJECT_CREATE_TIME_CANNOT_MODIFY);
            }
        }
        projectMapper.updateById(project);
        return ResponseResult.okResult(project.getProjectId());
    }

    @Override
    public ResponseResult delete(Integer projectId) {
        if(!projectExist(projectId)) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_NOT_EXISTS);
        }
        if(bugExist(projectId)) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_EXISTS_BUG);
        }

        projectMapper.deleteById(projectId);

        // 删除所有参与该项目的人员的参与关系
        LambdaQueryWrapper<UserProject> queryWrapperUP = new LambdaQueryWrapper<>();
        queryWrapperUP.eq(UserProject::getProjectId, projectId);
        userProjectMapper.delete(queryWrapperUP);

        // 删除项目下的所有模块
        LambdaQueryWrapper<ProjectModule> queryWrapperPM = new LambdaQueryWrapper<>();
        queryWrapperPM.eq(ProjectModule::getProjectId, projectId);
        projectModuleMapper.delete(queryWrapperPM);

        // 删除项目下的所有测试用例
        LambdaQueryWrapper<TestCase> queryWrapperTC = new LambdaQueryWrapper<>();
        queryWrapperTC.eq(TestCase::getProjectId, projectId);
        testCaseMapper.delete(queryWrapperTC);

        return ResponseResult.okResult(projectId);
    }

    @Override
    public ResponseResult addMember(Integer projectId, List<User> memberList) {
        for(User member : memberList) {
            UserProject userProject = new UserProject(member.getUserId(), projectId);
            userProjectMapper.insert(userProject);
        }
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(memberList, UserListVo.class);
        return ResponseResult.okResult(userListVos);
    }

    @Override
    public  ResponseResult deleteMember(UserProject userProject){
        LambdaQueryWrapper<UserProject> queryWrapperUP = new LambdaQueryWrapper<>();
        queryWrapperUP.eq(UserProject::getUserId, userProject.getUserId());
        queryWrapperUP.eq(UserProject::getProjectId, userProject.getProjectId());
        userProjectMapper.delete(queryWrapperUP);

        return ResponseResult.okResult(userProject);
    }

    @Override
    public ResponseResult statusSwitch(Project project) {
        projectMapper.updateById(project);
        ProjectStatusVo projectStatusVo = BeanCopyUtils.copyBean(project, ProjectStatusVo.class);
        return ResponseResult.okResult(projectStatusVo);
    }

    /*
    分页查询是一种在数据库查询中常用的技术，用于处理和显示大量数据的场景。
    它将大型数据集分隔成多个小部分（称为“页”），使用户能够一次只查看特定数量的记录，并能够在这些页面之间进行导航。
     */
    @Override
    public ResponseResult memberList(Integer pageNum, Integer pageSize, Integer projectId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.inSql(User::getUserId, "SELECT user_id FROM user_project WHERE project_id = " + projectId);
        Page<User> page = new Page<>(pageNum, pageSize);
        // 执行分页查询并获取用户列表
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);

        // 获取查询结果列表
        List<User> userList = userPage.getRecords();
        // 将User转换成UserListVo
        List<UserListVo> userListVoList = userList.stream()
                .map(user -> {
                    String roleName = "";
                    // 判断 roleId 并设置对应的 roleName
                    if (Objects.equals(user.getRoleId(), SystemConstants.TEST_ENGINEER)) {
                        roleName = "测试工程师";
                    } else if (Objects.equals(user.getRoleId(), SystemConstants.DEVELOPMENT_ENGINEER)) {
                        roleName = "开发工程师";
                    } else if (Objects.equals(user.getRoleId(), SystemConstants.PRODUCT_MANAGER)) {
                        roleName = "项目经理";
                    }
                    // 创建 UserListVo 对象并返回
                    return new UserListVo(user.getUserId(), user.getUsername(), roleName);
                })
                .collect(Collectors.toList());
        // 将查询结果封装到 ResponseResult 返回
        return ResponseResult.okResult(new PageVo(userListVoList, page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }

    public boolean projectExist(Integer projectId) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Project::getProjectId, projectId);
        return  count(queryWrapper) > 0;
    }

    public boolean bugExist(Integer projectId) {
        LambdaQueryWrapper<Bug> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bug::getBugProject, projectId);
        return bugMapper.selectCount(queryWrapper) > 0;
    }
}
