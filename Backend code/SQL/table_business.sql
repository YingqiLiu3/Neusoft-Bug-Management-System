CREATE DATABASE IF NOT EXISTS bugtest;

USE bugtest;

DROP TABLE IF EXISTS test_project;
DROP TABLE IF EXISTS project_module;
DROP TABLE IF EXISTS test_case;
DROP TABLE IF EXISTS test_bug;
DROP TABLE IF EXISTS user_project;

-- 项目表
CREATE TABLE test_project (
        project_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '项目ID',
        project_name VARCHAR(32) NOT NULL COMMENT '项目名称',
        project_description TEXT NOT NULL COMMENT '项目描述',
        project_created_by VARCHAR(16) NOT NULL COMMENT '项目负责人',
        project_created_time VARCHAR(32) NOT NULL COMMENT '创建时间',
        project_status TINYINT(1) DEFAULT 1 COMMENT '项目状态'
);

-- 模块表
CREATE TABLE project_module (
        module_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '模块ID',
        module_name VARCHAR(16) NOT NULL COMMENT '模块名称',
        module_description TEXT NOT NULL COMMENT '模块描述',

        project_id INT NOT NULL COMMENT '模块所属项目ID'
);

-- 测试用例表
CREATE TABLE test_case (
        test_case_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '测试用例ID',
        test_case_name VARCHAR(255) NOT NULL COMMENT '测试用例名称',
        test_purpose TEXT NOT NULL COMMENT '测试目的',
        preconditions TEXT NOT NULL COMMENT '前置条件',
        test_steps TEXT NOT NULL COMMENT '测试步骤',
        expected_result TEXT NOT NULL COMMENT '预期结果',
        priority ENUM('高', '中', '低') NOT NULL COMMENT '优先级',
        creator VARCHAR(16) NOT NULL COMMENT '用例创建人员',

        project_id INT NOT NULL COMMENT '用例所输项目ID',
        module_id INT NOT NULL COMMENT '用例所属模块ID'
);

-- Bug表
CREATE TABLE test_bug (
        bug_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Bug ID',
        bug_name VARCHAR(32) NOT NULL COMMENT 'Bug标题',
        bug_content TEXT COMMENT 'Bug内容',
        product_manager VARCHAR(16) NOT NULL COMMENT '项目负责人',
        bug_builder VARCHAR(16) NOT NULL COMMENT 'Bug创建人',
        bug_receiver VARCHAR(16) COMMENT '分配给谁',
        bug_grade ENUM('高', '中', '低') NOT NULL COMMENT 'Bug等级',
        bug_reexamine_time VARCHAR(32) DEFAULT NULL COMMENT '重审时间',
        bug_create_time VARCHAR(32) NOT NULL COMMENT '创建时间',
        bug_solved_time VARCHAR(32) COMMENT '解决时间',
        bug_state ENUM('新创建', '待修改', '修改中', '暂不修改', '待协商', '已修改', '已完成', '永不修改') DEFAULT '新创建' COMMENT 'Bug状态',

        bug_project INT NOT NULL COMMENT '所属项目ID',
        bug_module INT NOT NULL COMMENT '所属模块ID',
        test_case_id INT NOT NULL COMMENT '关联的测试用例ID'
);

-- 用户项目关联表
CREATE TABLE user_project (
        user_id INT NOT NULL COMMENT '用户ID',
        project_id INT NOT NULL COMMENT '项目ID',
        PRIMARY KEY (user_id, project_id)
);
