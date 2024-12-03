package com.dlut.bugtestmanage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterVo {
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
}
