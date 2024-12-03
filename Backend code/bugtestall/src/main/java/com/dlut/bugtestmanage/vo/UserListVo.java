package com.dlut.bugtestmanage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListVo {
    private Integer userId;
    private String username;
    private String role;
}
