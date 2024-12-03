package com.dlut.bugtestmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.constants.SystemConstants;
import com.dlut.bugtestmanage.entity.*;
import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;
import com.dlut.bugtestmanage.exception.SystemException;
import com.dlut.bugtestmanage.mapper.*;
import com.dlut.bugtestmanage.service.BugService;
import com.dlut.bugtestmanage.utils.BeanCopyUtils;
import com.dlut.bugtestmanage.vo.BugVo;
import com.dlut.bugtestmanage.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * (Bug)表服务实现类
 */
@Service("bugService")
public class BugServiceImpl extends ServiceImpl<BugMapper, Bug> implements BugService {

    @Autowired
    private BugMapper bugMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    @Autowired
    private UserProjectMapper userProjectMapper;

    @Override
    public ResponseResult createBug(Bug bug) {
        //对数据进行非空判断
        if (!StringUtils.hasText(bug.getBugName())) {
            throw new SystemException(AppHttpCodeEnum.BUG_NAME_NOT_EXISTS);
        }
        //项目所属的模块不存在
        if (!StringUtils.hasText(String.valueOf(bug.getBugModule()))) {
            throw new SystemException(AppHttpCodeEnum.BUG_MODULE_NOT_EXISTS);
        }
        //BUG内容不存在
        if (!StringUtils.hasText(bug.getBugContent())) {
            throw new SystemException(AppHttpCodeEnum.BUG_CONTENT_NOT_EXISTS);
        }
        if (!StringUtils.hasText(bug.getBugCreateTime())) {
            throw new SystemException(AppHttpCodeEnum.BUG_CREATE_TIME_ERROR);
        }
        if (!StringUtils.hasText(bug.getProductManager())) {
            throw new SystemException(AppHttpCodeEnum.BUG_PROJECT_MANAGER);
        }
        if (!StringUtils.hasText(bug.getBugGrade())) {
            throw new SystemException(AppHttpCodeEnum.BUG_GRADE_ERROR);
        }
        // 对创建人进行合法性判断
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getUsername, bug.getBugBuilder());
        User user = userMapper.selectOne(queryWrapper1);

        if (user == null) {
            throw new SystemException(AppHttpCodeEnum.BUG_BUILDER_NOT_EXISTS);
        } else if (!Objects.equals(user.getRoleId(), SystemConstants.TEST_ENGINEER)) {
            throw new SystemException(AppHttpCodeEnum.BUG_CREATOR_AUTH_ERROR);
        }
        //对所属项目合法性判断
        boolean valid = isValid(user.getUserId(), bug.getBugProject(), bug.getBugModule());
        if (!valid) {
            throw new SystemException(AppHttpCodeEnum.RALATIONSHIP_NOT_VALID);
        }


        save(bug);
        //返回给前端的数据
        return ResponseResult.okResult(200, bug.getBugName());

    }

    public boolean isValid(Integer userId, Integer bugProject, Integer bugModule) {
        boolean flag = true;
        // 使用 LambdaQueryWrapper 创建查询条件
        LambdaQueryWrapper<UserProject> queryWrapper1 = new LambdaQueryWrapper<>();
        // eq 方法用于添加条件，即 UserProject 表中的 userId 字段等于传入的 userId，并且项目 ID 等于 bugProject
        queryWrapper1.eq(UserProject::getUserId, userId);
        queryWrapper1.eq(UserProject::getProjectId, bugProject);
        UserProject userProject = userProjectMapper.selectOne(queryWrapper1);
        if (userProject == null) {
            flag = false;
        }

        LambdaQueryWrapper<ProjectModule> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(ProjectModule::getModuleId, bugModule);
        ProjectModule projectModule = projectModuleMapper.selectOne(queryWrapper2);

        if (projectModule.getProjectId() != bugProject) {
            flag = false;
        }

        return flag;
    }

    @Override
    public ResponseResult deleteBug(Integer bugId) {
        LambdaQueryWrapper<Bug> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Bug::getBugId, bugId);
        Bug bug = bugMapper.selectOne(queryWrapper1);
        if(bug == null) {
            throw new SystemException(AppHttpCodeEnum.BUG_NOT_EXISTS);
        }
        bugMapper.deleteById(bugId);
        return ResponseResult.okResult(bugId);
    }

    @Override
    public ResponseResult updateBug(Bug bug){
        if(!bugExist(bug.getBugId())) {
            throw new SystemException(AppHttpCodeEnum.BUG_NOT_EXISTS);
        }
        bugMapper.updateById(bug);
        return ResponseResult.okResult(200,bug.getBugName());
    }

    @Override
    public ResponseResult getBugDetail(Integer bugId) {
        if(!bugExist(bugId)) {
            throw new SystemException(AppHttpCodeEnum.BUG_NOT_EXISTS);
        }
        Bug bug = bugMapper.selectById(bugId);

        return ResponseResult.okResult(bug);

    }

    @Override
    public ResponseResult solveBug(Integer bugId) {
        if(!bugExist(bugId)) {
            throw new SystemException(AppHttpCodeEnum.BUG_NOT_EXISTS);
        }
        Bug bug = bugMapper.selectById(bugId);
        bug.setBugState("已修改");
        BugVo bugVo = BeanCopyUtils.copyBean(bug, BugVo.class);
        return ResponseResult.okResult(bugVo);

    }

    @Override
    public ResponseResult page(Integer current, Integer size, Integer projectId) {
        LambdaQueryWrapper<Bug> wrapper = new LambdaQueryWrapper<>();
        //推荐写法
        if (!"".equals(projectId)) {
            //精确查询
            wrapper.eq(Bug::getBugProject, projectId);

            //模糊查询
            //wrapper.like(ProjectModule::getProjectId, projectId);
        }
        Page<Bug> page = page(new Page<>(current, size), wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }//



    //检验bugId是否真实存在
    public boolean bugExist(Integer bugId) {
        LambdaQueryWrapper<Bug> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bug::getBugId, bugId);
        return bugMapper.selectCount(queryWrapper) > 0;
    }




}
