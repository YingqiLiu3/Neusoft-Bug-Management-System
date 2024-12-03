package com.dlut.bugtestmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Bug;
import com.dlut.bugtestmanage.entity.ProjectModule;
import com.dlut.bugtestmanage.entity.TestCase;
import com.dlut.bugtestmanage.entity.UserProject;
import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;
import com.dlut.bugtestmanage.exception.SystemException;
import com.dlut.bugtestmanage.mapper.BugMapper;
import com.dlut.bugtestmanage.mapper.ProjectModuleMapper;
import com.dlut.bugtestmanage.mapper.TestCaseMapper;
import com.dlut.bugtestmanage.service.ProjectModuleService;
import com.dlut.bugtestmanage.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * (ProjectModule)表服务实现类
 */
@Service("projectModuleService")
public class ProjectModuleServiceImpl extends ServiceImpl<ProjectModuleMapper, ProjectModule> implements ProjectModuleService {
    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    @Autowired
    private BugMapper bugMapper;

    @Autowired
    private TestCaseMapper testCaseMapper;

    @Transactional
    @Override
    public ResponseResult creatProjectModule(ProjectModule projectModule) {
        //对数据进行非空判断
        if (!StringUtils.hasText(projectModule.getModuleName())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_MODULE_NAME_NOT_NULL);
        }
        if (!StringUtils.hasText(projectModule.getModuleDescription())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_MODULE_DESCRIPTION_NOT_NULL);
        }

        //保存模块
        save(projectModule);
        return ResponseResult.okResult(projectModule);  //返回模块所属项目
    }

    //获取模块详细信息(模块存在还是项目存在就返回模块信息)
    @Override
    public ResponseResult getProjectModuleDetail(Integer moduleId) {
        if(!projectModuleExist(moduleId)) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_MODULE_NOT_EXISTS);
        }
        ProjectModule projectModule = projectModuleMapper.selectById(moduleId);
        return ResponseResult.okResult(projectModule);
    }

    @Override
    public ResponseResult submitProjectModuleInfo(ProjectModule projectModule) {
        if(!projectModuleExist(projectModule.getModuleId())) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_MODULE_NOT_EXISTS);
        }
        projectModuleMapper.updateById(projectModule);
        return ResponseResult.okResult(projectModule.getModuleId());
    }
    //项目是否存在
    public boolean projectModuleExist(Integer moduleId) {
        LambdaQueryWrapper<ProjectModule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectModule::getModuleId, moduleId);
        return  projectModuleMapper.selectCount(queryWrapper) > 0;
    }

    //模块删除
    @Override
    public ResponseResult delete(Integer moduleId) {
        if(!projectModuleExist(moduleId)) {
            throw new SystemException(AppHttpCodeEnum.PROJECT_MODULE_NOT_EXISTS);
        }
        if(bugExist(moduleId)) {
            throw new SystemException(AppHttpCodeEnum.MODULE_EXISTS_BUG);
        }
        projectModuleMapper.deleteById(moduleId);

        // 删除模块下的所有测试用例
        LambdaQueryWrapper<TestCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestCase::getModuleId, moduleId);
        testCaseMapper.delete(queryWrapper);

        return ResponseResult.okResult(moduleId);
    }


    public boolean bugExist(Integer moduleId) {
        LambdaQueryWrapper<Bug> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bug::getBugModule, moduleId);
        return bugMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public ResponseResult page(Integer current, Integer size, Integer projectId) {
        LambdaQueryWrapper<ProjectModule> wrapper = new LambdaQueryWrapper<>();
        //推荐写法
        if (!"".equals(projectId)) {
            //精确查询
            wrapper.eq(ProjectModule::getProjectId,projectId);

            //模糊查询
            //wrapper.like(ProjectModule::getProjectId, projectId);
        }
        Page<ProjectModule> page = page(new Page<>(current, size), wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }

}
