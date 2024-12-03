package com.dlut.bugtestmanage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusVo {
    private Integer projectId;
    private Boolean projectStatus;
}
