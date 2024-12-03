package com.dlut.bugtestmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO 是用于传输数据的对象，通常在应用的不同层之间交换数据时使用。它们的主要目标是封装和传输应用程序中的数据，并避免直接暴露实体类的复杂性或敏感信息。
 * DTO 通常不包含业务逻辑，仅用于持有数据。
 * 可能会聚合来自多个实体的数据，形成一个更符合前端需求的数据结构。
 * 在网络传输（如REST API）时使用，能够减少数据量和简化数据结构。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String username;  // 用户名
    private String role;      // 用户角色
}
