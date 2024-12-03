package com.dlut.bugtestmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Project;
import com.dlut.bugtestmanage.entity.TestCase;


/**
 * (TestCase)表服务接口
 */
public interface TestCaseService extends IService<TestCase> {
    ResponseResult createTestCase(TestCase testCase);

    ResponseResult update(TestCase testCase);

    ResponseResult page(Integer current, Integer size, Integer projectId);

    ResponseResult delete(Integer testCaseId);
}
