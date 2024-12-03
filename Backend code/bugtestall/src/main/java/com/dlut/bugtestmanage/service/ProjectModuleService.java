package com.dlut.bugtestmanage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Project;
import com.dlut.bugtestmanage.entity.ProjectModule;


/**
 * (ProjectModule)表服务接口
 */
public interface ProjectModuleService extends IService<ProjectModule> {
    ResponseResult creatProjectModule(ProjectModule projectModule);    //保存店铺

    ResponseResult getProjectModuleDetail(Integer moduleId);    //得到模块详细信息

    ResponseResult submitProjectModuleInfo(ProjectModule projectModule);

    ResponseResult delete(Integer moduleId);

    //分页查询
    ResponseResult page(Integer current, Integer size, Integer projectId);
}
