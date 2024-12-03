package com.dlut.bugtestmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Menu;


/**
 * (Menu)表服务接口
 */
public interface MenuService extends IService<Menu> {

    ResponseResult getMenus();
}
