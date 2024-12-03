package com.dlut.bugtestmanage.vo;

import com.dlut.bugtestmanage.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<Menu> data;
}