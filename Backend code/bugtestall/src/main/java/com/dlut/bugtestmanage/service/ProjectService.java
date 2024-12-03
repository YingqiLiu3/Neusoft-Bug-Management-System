package com.dlut.bugtestmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Project;
import com.dlut.bugtestmanage.entity.User;
import com.dlut.bugtestmanage.entity.UserProject;

import java.util.List;


/**
 * (Project)表服务接口
 */
public interface ProjectService extends IService<Project> {

    ResponseResult create(Project project);

    ResponseResult getProjects(String query);

    ResponseResult getProjectDetail(Integer projectId);

    ResponseResult submitProjectInfo(Project project);

    ResponseResult delete(Integer projectId);

    ResponseResult addMember(Integer projectId, List<User> memberList);

    ResponseResult deleteMember(UserProject userProject);

    ResponseResult statusSwitch(Project project);

    ResponseResult memberList(Integer pageNum, Integer pageSize, Integer projectId);
}
