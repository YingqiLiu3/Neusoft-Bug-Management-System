package com.dlut.bugtestmanage.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT（JSON Web Token）是一种开放标准 (RFC 7519)，用于在网络应用环境中以紧凑且自主的方式安全地传输信息。
 * JWT 最常用于用户身份验证。用户在登录时获得 JWT，后续请求可以用这个令牌进行身份验证，而不需要在每次请求中发送用户名和密码。
 */

@Component
public class JwtUtil {

    // 使用固定的 Base64 编码密钥
    private static final String BASE64_SECRET_KEY = "lqxp11lucUGBsT+iB8aD9s7pz4L9+vQ5xh7SgqfVlbQ=";
    private static final SecretKey SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(BASE64_SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

    // 生成 JWT，保存用户名和角色信息
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // 在 JWT 中保存角色信息
        return createToken(claims, username);
    }

    // 创建 JWT
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30)) // 30小时有效期(为了与前端相同)
                .signWith(SECRET_KEY)  // 使用固定密钥签名
                .compact();
    }

    // 验证 JWT
    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));  // 用户名相同且token没有过期
        } catch (ExpiredJwtException e) {
            // 捕获过期的异常并返回 false
            return false;
        } catch (Exception e) {
            // 处理其他异常
            return false;
        }
    }

    // 提取用户名
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 提取用户角色
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 提取过期时间
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // 检查 Token 是否过期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 解析 Token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    // 测试方法
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        // String token = jwtUtil.generateToken("ljf", "开发工程师");;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoi5byA5Y-R5bel56iL5biIIiwic3ViIjoibGpmIiwiaWF0IjoxNzMwOTQ1MTEyLCJleHAiOjE3MzEwNTMxMTJ9.Fvh2OWWF-wTQgfyyqQhcY2c4hVhZ99-G3UoVG19vPkw";
        System.out.println("Generated Token: " + token);

        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);
        System.out.println("Extracted Username: " + username);
        System.out.println("Extracted Role: " + role);

        boolean isValid = jwtUtil.validateToken(token, username);
        System.out.println("Is Token Valid: " + isValid);
    }
}
