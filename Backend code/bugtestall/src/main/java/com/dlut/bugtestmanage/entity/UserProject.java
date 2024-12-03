package com.dlut.bugtestmanage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (UserProject)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_project")
public class UserProject {

    // 标注一个字段为逻辑主键
    @TableId(value = "user_id")
    private Integer userId;

    // 另一个作为普通字段
    @TableField(value = "project_id")
    private Integer projectId;
}
