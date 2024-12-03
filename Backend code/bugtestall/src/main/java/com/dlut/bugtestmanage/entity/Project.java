package com.dlut.bugtestmanage.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Project)表实体类
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_project")
public class Project  {
    
    @TableId
    private Integer projectId;


    private String projectName;

    private String projectDescription;

    private String projectCreatedBy;

    private String projectCreatedTime;

    private Boolean projectStatus;
    
}
