CREATE DATABASE IF NOT EXISTS bugtest;

USE bugtest;

DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_menu;
DROP TABLE IF EXISTS role_menu;

-- 用户表
CREATE TABLE sys_user (
        user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
        username VARCHAR(16) NOT NULL COMMENT '用户名',
        password VARCHAR(255) NOT NULL COMMENT '密码',
        email VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
        phone_number VARCHAR(16) DEFAULT NULL COMMENT '手机号',

        role_id INT NOT NULL COMMENT '角色ID',
        project_id INT DEFAULT NULL COMMENT '项目ID',

        token varchar(255) DEFAULT '' COMMENT 'token',
        status char(1) DEFAULT '0' COMMENT '登录状态: 0代表未登录, 1代表已登录',
        UNIQUE (username)
);

-- 角色表
CREATE TABLE sys_role (
        role_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
        role_name VARCHAR(16) NOT NULL COMMENT '角色名称',
        UNIQUE (role_name)
);

-- 权限表
CREATE TABLE sys_menu (
        id INT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
        auth_name VARCHAR(16) NOT NULL COMMENT '权限名称',
        path VARCHAR(32) DEFAULT NULL COMMENT '前端路由',
        pid INT NOT NULL COMMENT '父权限ID',
        PRIMARY KEY (id)
);

INSERT INTO sys_menu (id, auth_name, path, pid) VALUES
(1, '项目管理', NULL, 0),
(10, '项目列表', 'projects', 1),
(100, '项目新增', NULL, 10),
(101, '项目编辑', NULL, 10),
(102, '项目删除', NULL, 10),
(103, '项目开关', NULL, 10),

(2, '测试用例管理', NULL, 0),
(20, '测试用例列表', 'usecases', 2),
(200, '测试用例新增', NULL, 20),
(201, '测试用例编辑', NULL, 20),
(202, '测试用例删除', NULL, 20),

(3, 'Bug管理', NULL, 0),
(30, 'Bug列表', 'bugs', 3),
(300, 'Bug新增', NULL, 30),
(301, 'Bug编辑', NULL, 30),
(302, 'Bug删除', NULL, 30),

(4, '统计管理', NULL, 0),
(40, '统计图表', 'computed', 4);

-- 角色权限关联表
CREATE TABLE role_menu (
        role_id INT NOT NULL COMMENT '角色ID',
        menu_id INT NOT NULL COMMENT '权限ID',
        PRIMARY KEY (role_id, menu_id)
);

-- 1 项目经理 2 测试工程师 3 开发工程师
INSERT INTO role_menu (role_id, menu_id) VALUES
(1, 1),
(1, 10),
(1, 100),
(1, 101),
(1, 102),
(1, 103),
(1, 2),
(1, 20),
(1, 201),
(1, 3),
(1, 30),
(1, 301),
(1, 4),
(1, 40),

(2, 1),
(2, 10),
(2, 2),
(2, 20),
(2, 200),
(2, 201),
(2, 202),
(2, 3),
(2, 30),
(2, 300),
(2, 301),
(2, 302),
(2, 4),
(2, 40),

(3, 1),
(3, 10),
(3, 2),
(3, 20),
(3, 3),
(3, 30),
(3, 301),
(3, 302),
(3, 4),
(3, 40);