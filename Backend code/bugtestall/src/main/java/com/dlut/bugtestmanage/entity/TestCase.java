package com.dlut.bugtestmanage.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TestCase)表实体类
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_case")
public class TestCase  {
    //测试用例ID    
    @TableId
    private Integer testCaseId;

    //测试用例名称
    private String testCaseName;
    //测试目的
    private String testPurpose;
    //前置条件
    private String preconditions;
    //测试步骤
    private String testSteps;
    //预期结果
    private String expectedResult;
    //优先级
    private String priority;
    //用例创建人员
    private String creator;
    //用例所属项目ID
    private Integer projectId;
    //用例所属模块ID
    private Integer moduleId;
    
}
