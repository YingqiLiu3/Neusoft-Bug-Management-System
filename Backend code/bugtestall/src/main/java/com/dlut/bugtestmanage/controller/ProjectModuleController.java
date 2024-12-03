package com.dlut.bugtestmanage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Project;
import com.dlut.bugtestmanage.entity.ProjectModule;
import com.dlut.bugtestmanage.service.ProjectModuleService;
import com.dlut.bugtestmanage.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules")
public class ProjectModuleController {
    @Autowired
    private ProjectModuleService projectModuleService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/add")
    public ResponseResult creatProjectModule(@RequestBody ProjectModule projectModule) {
        return projectModuleService.creatProjectModule(projectModule);
    }

    @GetMapping("/detail/{moduleId}")
    public ResponseResult getProjectModuleDetail(@PathVariable("moduleId") Integer moduleId){
        return projectModuleService.getProjectModuleDetail(moduleId);
    }

    //修改模块
    @PutMapping("/submit")
    public ResponseResult submitProjectModuleInfo(@RequestBody ProjectModule projectModule) {
        return projectModuleService.submitProjectModuleInfo(projectModule);
    }

    @DeleteMapping("/delete/{moduleId}")
    public ResponseResult delete(@PathVariable("moduleId") Integer moduleId) {
        return projectModuleService.delete(moduleId);
    }

    @GetMapping("/list/{projectId}")
    public ResponseResult page(
            @PathVariable("projectId") Integer projectId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "1") Integer size
    ){
        return projectModuleService.page( current, size,projectId);
    }

}
