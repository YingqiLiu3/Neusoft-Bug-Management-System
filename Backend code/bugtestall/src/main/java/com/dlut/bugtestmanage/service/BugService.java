package com.dlut.bugtestmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Bug;


/**
 * (Bug)表服务接口
 */
public interface BugService extends IService<Bug> {

    ResponseResult createBug(Bug bug);

    ResponseResult deleteBug(Integer bugId);

    ResponseResult updateBug(Bug bug);

    ResponseResult getBugDetail(Integer bugId);

    ResponseResult solveBug(Integer bugId);

    ResponseResult page(Integer current, Integer size, Integer projectId);
}//
