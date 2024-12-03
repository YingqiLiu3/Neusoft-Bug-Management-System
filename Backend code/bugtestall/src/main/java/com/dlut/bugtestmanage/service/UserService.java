package com.dlut.bugtestmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.User;


/**
 * (User)表服务接口
 */
public interface UserService extends IService<User> {

    ResponseResult register(User user);

    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult showAllUser(Integer pageNum, Integer pageSize, Integer projectId);
}
