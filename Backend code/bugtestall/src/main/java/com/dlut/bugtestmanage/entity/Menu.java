package com.dlut.bugtestmanage.entity;


import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Menu)表实体类
 * SuppressWarnings("serial"):  这个注解用于抑制编译器的警告，表示该类是可序列化的，但没有显式定义 serialVersionUID 字段。
 * 在某些情况下，Java 会提醒开发者加上这个字段，以便在序列化和反序列化对象时保持兼容性。
 * Data: 这是 Lombok 提供的注解，用来自动生成 Java 类的常用方法，比如 getter、setter、toString()、equals() 和 hashCode() 等。这大大减少了样板代码。
 * AllArgsConstructor: 这个注解也是来自 Lombok，它会自动生成一个包含所有字段的构造函数。你可以使用该构造函数在创建对象的同时为所有属性赋值。
 * NoArgsConstructor: 该注解生成一个无参数的构造函数，方便在需要无参数构造时（例如用于某些框架的反射机制）使用。
 * Accessors(chain = true): 这个 Lombok 注解允许使用链式调用方式设置属性值。比如说，menu.setAuthName("name").setPath("/path") 这样的调用形式。
 * TableName("sys_menu"): 这个注解来自 MyBatis-Plus，它定义了这个实体类对应的数据库表的名称。这里，Menu 类对应于数据库中的 sys_menu 表。
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu  {
    //权限ID    
    @TableId
    private Integer id;

    //权限名称
    private String authName;
    //前端路由
    private String path;
    //父权限ID
    private Integer pid;

    @TableField(exist = false)
    private List<Menu> children;
    
}
