package com.dlut.bugtestmanage.utils;

import io.jsonwebtoken.security.Keys;
import lombok.var;

public class GenerateKey {
    public static void main(String[] args) {
        // 生成一个符合 HMAC-SHA256 要求的密钥
        var key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        // 输出 Base64 编码的密钥
        System.out.println("Base64 Encoded Secret Key: " + java.util.Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
