package com.dlut.bugtestmanage.utils;

import com.dlut.bugtestmanage.dto.UserInfo;

public class UserThreadLocalUtil {

    private final static ThreadLocal<UserInfo> USER_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程
    public static void setUser(UserInfo userInfo) {
        USER_THREAD_LOCAL.set(userInfo);
    }

    // 从线程中获取
    public static UserInfo getUser() {
        return USER_THREAD_LOCAL.get();
    }

    // 清理
    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}