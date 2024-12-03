package com.dlut.bugtestmanage.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User  {
    //用户ID    
    @TableId
    private Integer userId;

    //用户名
    private String username;
    //密码
    private String password;
    //邮箱
    private String email;
    //手机号
    private String phoneNumber;
    //角色ID
    private Integer roleId;
    //项目ID（用户与项目是多对多关系，此处为何会有项目id）
    private Integer projectId;
    //token
    private String token;
    //登录状态: 0代表未登录, 1代表已登录
    private String status;

    @TableField(exist = false)
    private String role;
    
}
