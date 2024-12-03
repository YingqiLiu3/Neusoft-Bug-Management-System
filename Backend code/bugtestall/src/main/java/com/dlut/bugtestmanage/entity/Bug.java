package com.dlut.bugtestmanage.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (Bug)表实体类
 * Entity 类代表数据库表的结构，通常与 ORM（如 JPA、Hibernate）一起使用。
 * 它能够映射数据库中的记录到 Java 对象，使得开发者可以以面向对象的方式对数据进行操作。
 */

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_bug")
public class Bug  {
    //Bug ID    
    @TableId
    private Integer bugId;

    //Bug标题
    private String bugName;
    //Bug内容
    private String bugContent;
    //项目负责人
    private String productManager;
    //Bug创建人
    private String bugBuilder;
    //分配给谁
    private String bugReceiver;
    //Bug等级
    private String bugGrade;
    //重审时间
    private String bugReexamineTime;
    //创建时间
    private String bugCreateTime;
    //解决时间
    private String bugSolvedTime;
    //Bug状态
    private String bugState;
    //所属项目ID
    private Integer bugProject;
    //所属模块ID
    private Integer bugModule;
    //关联的测试用例ID
    private Integer testCaseId;
    
}
