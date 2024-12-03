package com.dlut.bugtestmanage.controller;

import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;//注入接口

    @GetMapping("/menus")//映射地址
    public ResponseResult getMenus() {
        return menuService.getMenus();
    }
}
