package com.dlut.bugtestmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.dto.UserInfo;
import com.dlut.bugtestmanage.entity.Menu;
import com.dlut.bugtestmanage.entity.Role;
import com.dlut.bugtestmanage.mapper.MenuMapper;
import com.dlut.bugtestmanage.mapper.RoleMapper;
import com.dlut.bugtestmanage.service.MenuService;
import com.dlut.bugtestmanage.utils.UserThreadLocalUtil;
import com.dlut.bugtestmanage.vo.RoutersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Menu)表服务实现类
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseResult getMenus() {
        UserInfo userInfo = UserThreadLocalUtil.getUser();
        String role = userInfo.getRole();

        List<Menu> menus = menuMapper.findMenuNameListByRoleName(role);

        // 构建tree
        List<Menu> menuTree = buildMenuTree(menus, 0);

        RoutersVo routersVo = new RoutersVo(menuTree);

        // 封装数据返回
        return ResponseResult.okResult(routersVo);
    }

    // 构建tree -> 递归
    // 先找出第一层的菜单, 然后去找他们的子菜单设置到children属性中
    private List<Menu> buildMenuTree(List<Menu> menus, Integer pid) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getPid().equals(pid))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    // 获取传入菜单的子菜单
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m0 -> m0.getPid().equals(menu.getId()))
                .map(m1 -> m1.setChildren(getChildren(m1, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}
