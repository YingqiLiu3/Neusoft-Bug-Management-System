package com.dlut.bugtestmanage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO 是用于表现层（View Layer）的对象，通常用于在前端展示数据。它们通常需要对数据进行格式化或计算，便于展示给用户。
 * VO 通常不会直接映射到数据库，它们更关注于视图（UI）的构建。
 * 可以包含格式化的字段、计算得出的值或组合多个 DTO 的属性。
 * 在处理视图层逻辑时很有用，例如在 API 响应中返回需要的字段，同时可以进行字段重命名来提高可读性。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugVo {
    private Integer bugId;
    private String bugState;
}
