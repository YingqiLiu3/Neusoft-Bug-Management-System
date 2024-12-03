package com.dlut.bugtestmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlut.bugtestmanage.entity.Menu;
import com.dlut.bugtestmanage.entity.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (Project)表数据库访问层
 */
public interface ProjectMapper extends BaseMapper<Project> {

    List<Project> findProjectsListByUserName(String username, String role);

    List<Project> findProjectsListByQuery(String username, String role, String query);
}
