package com.dlut.bugtestmanage.controller;

import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.dto.UserInfo;
import com.dlut.bugtestmanage.entity.Project;
import com.dlut.bugtestmanage.entity.User;
import com.dlut.bugtestmanage.entity.UserProject;
import com.dlut.bugtestmanage.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseResult create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping
    public ResponseResult getProjects(@RequestParam("query") String query) {
        return projectService.getProjects(query);
    }

    @GetMapping("/detail/{projectId}")
    public ResponseResult getProjectDetail(@PathVariable("projectId") Integer projectId) {
        return projectService.getProjectDetail(projectId);
    }

    @PutMapping("/submit")
    public ResponseResult submitProjectInfo(@RequestBody Project project) {
        return projectService.submitProjectInfo(project);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseResult delete(@PathVariable("projectId") Integer projectId) {
        return projectService.delete(projectId);
    }

    @PostMapping("/addMember")
    public ResponseResult addMember(
            @RequestParam Integer projectId,
            @RequestBody Map<String, List<User>> members) {
        return projectService.addMember(projectId, members.get("members"));
    }

    @DeleteMapping("/deleteMember")
    public ResponseResult deleteMember(@RequestBody UserProject userProject) {
        return projectService.deleteMember(userProject);
    }

    @GetMapping("/memberList")
    public ResponseResult memberList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectId") Integer projectId
    ) {
        return projectService.memberList(pageNum, pageSize, projectId);
    }

    @PutMapping("/statusSwitch/{projectId}")
    public ResponseResult statusSwitch(@RequestBody Project project) {
        return projectService.statusSwitch(project);
    }
}
