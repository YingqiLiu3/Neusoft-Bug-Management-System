package com.dlut.bugtestmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlut.bugtestmanage.entity.Menu;

import java.util.List;


/**
 * (Menu)表数据库访问层
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findMenuNameListByRoleName(String role);

}
