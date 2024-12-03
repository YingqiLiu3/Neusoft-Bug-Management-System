package com.dlut.bugtestmanage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Bug;
import com.dlut.bugtestmanage.entity.TestCase;
import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;
import com.dlut.bugtestmanage.exception.SystemException;
import com.dlut.bugtestmanage.mapper.BugMapper;
import com.dlut.bugtestmanage.mapper.TestCaseMapper;
import com.dlut.bugtestmanage.service.TestCaseService;
import com.dlut.bugtestmanage.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * (TestCase)表服务实现类
 */
@Service("testCaseService")
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper,TestCase> implements TestCaseService {

    @Autowired
    private TestCaseMapper testCaseMapper;

    @Autowired
    private BugMapper bugMapper;

    @Transactional
    @Override
    public ResponseResult createTestCase(TestCase testCase) {
        //对数据进行非空判断
        if (!StringUtils.hasText(testCase.getTestCaseName())) {
            throw new SystemException(AppHttpCodeEnum.TEST_CASE_NAME_NOT_NULL);
        }
        if (!StringUtils.hasText(testCase.getTestPurpose())) {
            throw new SystemException(AppHttpCodeEnum.TEST_PURPOSE_NOT_NULL);
        }
        if (!StringUtils.hasText(testCase.getPreconditions())) {
            throw new SystemException(AppHttpCodeEnum.PREDICTIONS_NOT_NULL);
        }
        if (!StringUtils.hasText(testCase.getTestSteps())) {
            throw new SystemException(AppHttpCodeEnum.TEST_STEPS_NOT_NULL);
        }
        if (!StringUtils.hasText(testCase.getExpectedResult())) {
            throw new SystemException(AppHttpCodeEnum.EXPECTED_RESULT_NOT_NULL);
        }

        //保存模块
        save(testCase);
        return ResponseResult.okResult(testCase);   //返回测试用例
    }


    @Override
    public ResponseResult update(TestCase testCase) {
        testCaseMapper.updateById(testCase);
        return ResponseResult.okResult(200, "测试用例更改成功");
    }

    @Override
    public ResponseResult page(Integer current, Integer size, Integer projectId) {
        LambdaQueryWrapper<TestCase> wrapper = new LambdaQueryWrapper<>();
        //推荐写法
        if (!"".equals(projectId)) {
            //精确查询
            wrapper.eq(TestCase::getProjectId, projectId);

            //模糊查询
            //wrapper.like(ProjectModule::getProjectId, projectId);
        }
        Page<TestCase> page = page(new Page<>(current, size), wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }

    @Override
    public ResponseResult delete(Integer testCaseId){
        if(bugExist(testCaseId)) {
            throw new SystemException(AppHttpCodeEnum.TEST_CASE_EXISTS_BUG);
        }
        testCaseMapper.deleteById(testCaseId);
        return ResponseResult.okResult(testCaseId);
    }

    public boolean bugExist(Integer testCaseId) {
        LambdaQueryWrapper<Bug> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bug::getTestCaseId, testCaseId);
        return bugMapper.selectCount(queryWrapper) > 0;
    }
}
