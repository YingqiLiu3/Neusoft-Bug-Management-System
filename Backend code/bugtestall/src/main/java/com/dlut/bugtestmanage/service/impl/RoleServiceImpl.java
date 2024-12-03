package com.dlut.bugtestmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.entity.Role;
import com.dlut.bugtestmanage.mapper.RoleMapper;
import com.dlut.bugtestmanage.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
