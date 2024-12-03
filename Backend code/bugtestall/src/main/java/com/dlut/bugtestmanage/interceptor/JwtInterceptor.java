package com.dlut.bugtestmanage.interceptor;

import com.dlut.bugtestmanage.dto.UserInfo;
import com.dlut.bugtestmanage.utils.JwtUtil;
import com.dlut.bugtestmanage.utils.UserThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义名为 JwtInterceptor 的 Spring Web MVC 拦截器，它用于处理基于 JWT（JSON Web Token）的身份验证。
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     *
     * @param request 当前的 HTTP 请求对象，用于获取请求头和参数。
     * @param response 当前的 HTTP 响应对象，用于设置响应状态。
     * @param handler 被处理的目标对象，通常是控制器中的处理方法。
     * @return 返回一个布尔值。true 表示继续执行后续的处理器，false 则中止请求处理。
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);
        System.out.println(role);

        if (jwtUtil.validateToken(token, username)) {
            // 如果令牌有效，将用户信息存入 ThreadLocal
            UserInfo userInfo = new UserInfo(username, role);
            UserThreadLocalUtil.setUser(userInfo);
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal，避免线程复用时的数据混淆
        UserThreadLocalUtil.clear();
    }
}
