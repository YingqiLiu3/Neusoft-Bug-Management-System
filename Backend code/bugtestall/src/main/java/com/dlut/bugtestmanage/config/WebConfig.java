package com.dlut.bugtestmanage.config;

import com.dlut.bugtestmanage.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 跨域配置
     * GET用于请求获取指定资源的信息（前端向后端请求数据）。
     * POST用于向指定资源提交数据，通常用来创建新的资源（前端将需要保存的数据传给后端）。
     * PUT用于更新指定资源的完整表示（前端将需要更新的数据传给后端）。
     * DELETE用于请求删除指定资源（后端删除指定数据）。
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配所有路径
                .allowedOriginPatterns("*") // 允许所有来源的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 允许携带凭证（如 Cookie）
    }

    // 拦截器配置，进行用户登录状态的拦截，日志的拦截等
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(
                        "/user/logout",
                        "/menus",
                        "/projects",
                        "/projects/create",
                        "/projects/detail/{projectId}",
                        "/projects/submit"
                ) // 拦截所有 /** 路径(需要token)
                .excludePathPatterns(
                        "/user/login",
                        "/user/regist"
                ); // 排除登录和注册接口(不需要token)
    }
}
