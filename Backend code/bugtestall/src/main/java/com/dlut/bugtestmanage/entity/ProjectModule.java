package com.dlut.bugtestmanage.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (ProjectModule)表实体类
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_module")
public class ProjectModule  {
    //模块ID    
    @TableId
    private Integer moduleId;

    //模块名称
    private String moduleName;
    //模块描述
    private String moduleDescription;
    //模块所属项目ID
    private Integer projectId;
    
}
