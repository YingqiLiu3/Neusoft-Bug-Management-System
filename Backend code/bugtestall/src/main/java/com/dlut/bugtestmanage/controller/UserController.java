package com.dlut.bugtestmanage.controller;

import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.User;
import com.dlut.bugtestmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout() {
        return userService.logout();
    }

    @GetMapping("/showAllUser")
    public ResponseResult showAllUser(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectId") Integer projectId) {
        return userService.showAllUser(pageNum, pageSize, projectId);
    }
}

