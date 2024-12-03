package com.dlut.bugtestmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.constants.SystemConstants;
import com.dlut.bugtestmanage.dto.UserInfo;
import com.dlut.bugtestmanage.entity.Role;
import com.dlut.bugtestmanage.entity.User;
import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;
import com.dlut.bugtestmanage.exception.SystemException;
import com.dlut.bugtestmanage.mapper.RoleMapper;
import com.dlut.bugtestmanage.mapper.UserMapper;
import com.dlut.bugtestmanage.service.UserService;
import com.dlut.bugtestmanage.utils.BeanCopyUtils;
import com.dlut.bugtestmanage.utils.JwtUtil;
import com.dlut.bugtestmanage.utils.PasswordEncoder;
import com.dlut.bugtestmanage.utils.UserThreadLocalUtil;
import com.dlut.bugtestmanage.vo.PageVo;
import com.dlut.bugtestmanage.vo.UserListVo;
import com.dlut.bugtestmanage.vo.UserLoginVo;
import com.dlut.bugtestmanage.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JwtUtil jwtUtil;

    // Override是重写基类中的一个方法
    @Override
    public ResponseResult register(User user) {
        // 对数据进行进行非空判断
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getRole())) {
            throw new SystemException(AppHttpCodeEnum.ROLETYPE_NOT_NULL);
        }

        // 获取用户输入的密码
        String password = user.getPassword();
        // 获取用户的用户名作为盐值
        String salt = user.getUsername();
        // 对密码进行加密（加盐的MD5）
        String encryptedPassword = PasswordEncoder.encodePassword(password, salt);
        //对旧密码进行一个更新
        user.setPassword(encryptedPassword);

        // 设置roleId
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();//构建查询条件
        queryWrapper.eq(Role::getRoleName, user.getRole());
        //实体类role中的getRoleName方法找与user传入的匹配
        Role role = roleMapper.selectOne(queryWrapper);//数据库查询返回单个结果
        if (role == null) {
            throw new SystemException(AppHttpCodeEnum.ROLETYPE_NOT_EXISTS);
        }
        user.setRoleId(role.getRoleId());

        if (userNameExist(user.getUsername())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_EXISTS);
        } else {
            // 用户不存在，将用户信息存入用户数据库
            save(user);

            // 封装返回数据
            UserRegisterVo userRegisterVo = BeanCopyUtils.copyBean(user, UserRegisterVo.class);
            return ResponseResult.okResult(userRegisterVo);
        }
    }

    @Override
    public ResponseResult login(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }

        // 获取用户输入的密码
        String password = user.getPassword();
        // 获取用户的用户名作为盐值
        String salt = user.getUsername();
        // 对输入密码进行加密（加盐的MD5）
        String encryptedPassword = PasswordEncoder.encodePassword(password, salt);

        // 查询数据库中的用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        if (dbUser == null) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXISTS);
        }

        // 比较数据库中存储的加密密码和用户输入的加密密码
        if (!dbUser.getPassword().equals(encryptedPassword)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        // 生成JWT令牌
        String username = dbUser.getUsername();
        /*
          修改Bug: 由于用户表没有角色名字段, 注册时只存入了roleId, 所以不能直接获取role, 会为空
         */
        Integer roleId = dbUser.getRoleId();
        Role role1 = roleMapper.selectById(roleId); //根据给定ID查询数据库
        String role = role1.getRoleName();
        String token = jwtUtil.generateToken(username, role);

        // 优化登录逻辑
        // 登录状态为0且无token -> 正常 -> 正常登录(将登录状态置为1, 设置token)
        // 登录状态为1且token过期 -> 用户未点退出登录且token过期 -> 返回token过期(将登录状态置为0, 将token清空)
        // 登录状态为1且token未过期 -> 用户未点退出登录且token未过期 -> 返回登录成功(返回token为数据库中的token)
        /*======================================================================================*/
        boolean hasToken = false;
        boolean isExpiration = false;
        String dbToken = dbUser.getToken();
        if (StringUtils.hasText(dbToken)) {
            hasToken = true;
            if (!jwtUtil.validateToken(dbToken, dbUser.getUsername())) {
                isExpiration = true;
            }
        }

        if (dbUser.getStatus().equals(SystemConstants.NOT_LOG_IN) && !hasToken) {
            // 赋值: jwt, status为"1"
            dbUser.setToken(token);
            dbUser.setStatus(SystemConstants.ALREADY_LOGGED);
        } else if (dbUser.getStatus().equals(SystemConstants.ALREADY_LOGGED) && isExpiration) {
            // 将登录状态置为0, 将token清空
            dbUser.setToken("");
            dbUser.setStatus(SystemConstants.NOT_LOG_IN);

            // 存入数据库
            updateById(dbUser);

            throw new SystemException(AppHttpCodeEnum.LOGIN_EXPIRATION);
        } else if (dbUser.getStatus().equals(SystemConstants.ALREADY_LOGGED) && !isExpiration) {
            // 封装数据返回
            UserLoginVo userLoginVo = new UserLoginVo(user.getUsername(), dbToken,role);
            return ResponseResult.okResult(userLoginVo);
        }
        /*======================================================================================*/

        // 存入数据库
        updateById(dbUser);

        // 封装数据返回
        UserLoginVo userLoginVo = new UserLoginVo(user.getUsername(), token,role);
        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 从 ThreadLocal 中获取当前登录用户信息
        UserInfo userInfo = UserThreadLocalUtil.getUser();  //userInfo是用户名+用户角色
        if (userInfo == null) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_ONLINE);
        }

        String username = userInfo.getUsername();

        // 根据用户名查找用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);

        // 用户不存在
        if (user == null) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXISTS);
        } else {
            // 用户未登录
            if (user.getStatus().equals(SystemConstants.NOT_LOG_IN)) {
                throw new SystemException(AppHttpCodeEnum.USER_NOT_ONLINE);
            } else {
                // 用户已登录
                // 置用户状态 status 为 "0"（未登录）
                user.setStatus(SystemConstants.NOT_LOG_IN);

                // 清除用户的 token
                user.setToken("");

                // 更新数据库
                userMapper.updateById(user);

                // 清除 ThreadLocal 中的用户信息
                UserThreadLocalUtil.clear();

                return ResponseResult.okResult(username);
            }
        }
    }

    @Override
    public ResponseResult showAllUser(Integer pageNum, Integer pageSize, Integer projectId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        // 查询roleId为2(测试工程师)或3(开发工程师)的用户
        queryWrapper.in(User::getRoleId, SystemConstants.TEST_ENGINEER, SystemConstants.DEVELOPMENT_ENGINEER);

        // 查询不属于该项目的用户
        queryWrapper.notInSql(User::getUserId, "SELECT user_id FROM user_project WHERE project_id = " + projectId);
        // 分页对象
        Page<User> page = new Page<>(pageNum, pageSize);
        // 执行分页查询并获取用户列表
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);

        // 获取查询结果列表
        List<User> userList = userPage.getRecords();
        // 将User转换成UserListVo
        List<UserListVo> userListVoList = userList.stream()
                .map(user -> {
                    String roleName = "";
                    // 判断 roleId 并设置对应的 roleName
                    if (Objects.equals(user.getRoleId(), SystemConstants.TEST_ENGINEER)) {
                        roleName = "测试工程师";
                    } else if (Objects.equals(user.getRoleId(), SystemConstants.DEVELOPMENT_ENGINEER)) {
                        roleName = "开发工程师";
                    }
                    // 创建 UserListVo 对象并返回
                    return new UserListVo(user.getUserId(), user.getUsername(), roleName);
                })
                .collect(Collectors.toList());
        // 将查询结果封装到 ResponseResult 返回
        return ResponseResult.okResult(new PageVo(userListVoList, page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }

    // 检查数据库中是否存在具有指定用户名的用户
    public boolean userNameExist(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return  count(queryWrapper) > 0;
    }
}
