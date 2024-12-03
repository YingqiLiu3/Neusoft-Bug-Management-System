CREATE DATABASE IF NOT EXISTS bugtest;

USE bugtest;

TRUNCATE TABLE sys_user;
TRUNCATE TABLE sys_role;
TRUNCATE TABLE test_project;
TRUNCATE TABLE project_module;
TRUNCATE TABLE test_case;
TRUNCATE TABLE test_bug;
TRUNCATE TABLE user_project;

/**
    sys_user: 用户表
 */
INSERT INTO sys_user (user_id, username, password, role_id) VALUES
(1, 'admin', '45f2603610af569b6155c45067268c6b', 1),
(2, 'test', '861f194e9d6118f3d942a72be3e51749', 2),
(3, 'dev', 'a9df8b78b7551855075a96e1f0f44b6c', 3),

(4, 'pm1', 'a7bc81fbb091bc8b73ecf78fb0326fa9', 1),
(5, 'pm2', '14374650f4d12e35c7f280dba63cebbe', 1),
(6, 'pm3', '4d5abeb8f1538f45226bd56abda44b9c', 1),
(7, 'pm4', 'd38d865135a7154e398c05d504a6629c', 1),

(8, 'test_1_1', 'f422839cac21293f989ccec824f2fb68', 2),
(9, 'test_1_2', 'b15e0bf7a57cdf1484525e135120a68c', 2),
(10, 'test_1_3', '326a0301d00696f5d94620406d466294', 2),
(11, 'test_1_4', 'fac919b5b23ed2ab0d36bd0d3c9cbd45', 2),
(12, 'test_1_5', '20ca5e43b7bf36c938caf682d5a3735e', 2),
(13, 'test_2_1', 'ba26d8662323854c415a72d9c40bcfe6', 2),
(14, 'test_2_2', 'ddd6344f3549a20f4009e5e2541c3df6', 2),
(15, 'test_2_3', '748a22af6ad6ab0a35fb6bd7bd441fc2', 2),
(16, 'test_2_4', '982ebabec6ebf754717ec514bbc09fe8', 2),
(17, 'test_2_5', '444ffb57daf3ecb51f85ab0a9445101f', 2),
(18, 'test_3_1', '0120009e7b18ffd474da4eff72ca98b0', 2),
(19, 'test_3_2', 'f09a3d635ad56b4d7ed41f738a443da9', 2),
(20, 'test_3_3', '328c45a5544f05d5037409739ab5ee68', 2),
(21, 'test_3_4', '077191063d3a92bb63016214e80c8bd0', 2),
(22, 'test_3_5', 'cea36e7bf57392f0398ff410c98853db', 2),
(23, 'test_4_1', '2ba913b1bf94b0d522d85972e0fa1de0', 2),
(24, 'test_4_2', 'b3cf539df55e182e0c4df58e0fb5eb81', 2),
(25, 'test_4_3', 'dbbfa4e33a2d48f80edcb203a5ed361a', 2),
(26, 'test_4_4', 'cc465fcb90baf8637cc20eb1e48f009b', 2),
(27, 'test_4_5', '222a27ae10cbaa8eb8e3880d4c6133b0', 2),

(28, 'dev_1_1', 'ba5b14e1ae13890122294d8cecd49821', 3),
(29, 'dev_1_2', 'b29feb7eb8fcffe5e701b9e29fcf1b5d', 3),
(30, 'dev_1_3', 'ec4ac22122da7e9833b928f5a778d07c', 3),
(31, 'dev_1_4', '3be65fac651ae021a0de0805050e8188', 3),
(32, 'dev_1_5', '54beda6a95a9301904076476dfe8d758', 3),
(33, 'dev_2_1', '127718f5fc51ac1449f90abd96f40f56', 3),
(34, 'dev_2_2', '66535cb713aef1fb8a0a6ead6d70213c', 3),
(35, 'dev_2_3', 'd28d2b8919d15f9b9c47433e7273421b', 3),
(36, 'dev_2_4', '17b7438630e14a945e2c737aa63d6f42', 3),
(37, 'dev_2_5', '8bf6c64efd711e0d94f05f4c47b166c6', 3),
(38, 'dev_3_1', '5f0a7904aa2e13cdabd643551b1ad7f8', 3),
(39, 'dev_3_2', '10fc0a75964d061290d6c7d8e59422ea', 3),
(40, 'dev_3_3', '2573cfaddc25a207992dd82bf4ef6b41', 3),
(41, 'dev_3_4', '5420500698e1c42bb91ee24bb094e456', 3),
(42, 'dev_3_5', 'c4b328d54fc139170385789e8ad2065c', 3),
(43, 'dev_4_1', '8ea8a932320fbf0cd5c52e837a126c5f', 3),
(44, 'dev_4_2', '2bab851023dda9663804be9a1dd30912', 3),
(45, 'dev_4_3', 'c4ed34dc5cbf114ef2daccf084d1938c', 3),
(46, 'dev_4_4', 'b028fa7ccce72adfb747ff01c64f381f', 3),
(47, 'dev_4_5', '8a2c85c9fc13c36d40c95ae445b07324', 3);

/**
    sys_role: 角色表
 */
INSERT INTO sys_role (role_id, role_name) VALUES
(1, '项目经理'),(2, '测试工程师'),(3, '开发工程师');

/**
    test_project: 项目表
 */
INSERT INTO test_project (project_name, project_description, project_created_by, project_created_time, project_status) VALUES
('Project Alpha', 'This is a description for Project Alpha.', 'pm1', NOW(), 1),
('Project Beta', 'This is a description for Project Beta.', 'pm2', NOW(), 1),
('Project Gamma', 'This is a description for Project Gamma.', 'pm3', NOW(), 1),
('Project Delta', 'This is a description for Project Delta.', 'pm4', NOW(), 1),
('Bug管理系统', '用于测试Bug', 'admin', NOW(), 1),
('酒店管理系统', '用于管理酒店', 'admin', NOW(), 1),
('12345', 'This is a description for Project 12345', 'admin', NOW(), 1);

/**
    project_module: 模块表
 */
INSERT INTO project_module (module_name, module_description, project_id) VALUES
('Module A1', 'Description for Module A1', 1),
('Module A2', 'Description for Module A2', 1),
('Module A3', 'Description for Module A3', 1),
('Module A4', 'Description for Module A4', 1),
('Module A5', 'Description for Module A5', 1),

('Module B1', 'Description for Module B1', 2),
('Module B2', 'Description for Module B2', 2),
('Module B3', 'Description for Module B3', 2),
('Module B4', 'Description for Module B4', 2),
('Module B5', 'Description for Module B5', 2),

('Module C1', 'Description for Module C1', 3),
('Module C2', 'Description for Module C2', 3),
('Module C3', 'Description for Module C3', 3),
('Module C4', 'Description for Module C4', 3),
('Module C5', 'Description for Module C5', 3),

('Module D1', 'Description for Module D1', 4),
('Module D2', 'Description for Module D2', 4),
('Module D3', 'Description for Module D3', 4),
('Module D4', 'Description for Module D4', 4),
('Module D5', 'Description for Module D5', 4),

('项目管理', 'Description for 项目管理', 5),
('模块管理', 'Description for 模块管理', 5),
('统计分析', 'Description for 统计分析', 5),
('Bug管理', 'Description for Bug管理', 5),
('测试用例管理', 'Description for 测试用例管理', 5),

('客房管理', 'Description for 客房管理', 6),
('员工管理', 'Description for 员工管理', 6),
('酒水管理', 'Description for 酒水分析', 6),
('反馈管理', 'Description for 反馈管理', 6),
('管理', 'Description for 管理', 6),

('1', 'Description for 1', 7),
('2', 'Description for 2', 7),
('3', 'Description for 3', 7),
('4', 'Description for 4', 7),
('5', 'Description for 5', 7);

/**
    test_case: 测试用例表(10列)  无test_case_id
 */

INSERT INTO test_case (test_case_name, test_purpose, preconditions, test_steps, expected_result, priority, creator, project_id, module_id)
VALUES
    -- Bug管理系统 - 测试用例
    ('Bug创建测试', '测试创建Bug的功能', '登录系统并拥有创建Bug的权限', '1. 进入Bug管理模块\n2. 点击创建Bug按钮\n3. 输入Bug信息并保存', 'Bug成功创建并显示在Bug列表中', '高', 'test', 5, 24),
    ('Bug修改测试', '测试修改Bug的功能', 'Bug已经存在', '1. 进入Bug管理模块\n2. 选择一个Bug并点击编辑\n3. 修改Bug信息并保存', 'Bug信息修改成功并反映在Bug列表中', '中', 'test', 5, 24),
    -- 酒店管理系统 - 测试用例
    ('客房预订功能测试', '测试用户是否能够成功预订客房', '用户已登录且有可用的房间', '1. 进入客房管理模块\n2. 选择房型并输入预订信息\n3. 提交预订请求', '预订成功，系统显示预订确认信息', '高', 'test', 6, 26),
    ('员工信息录入测试', '测试管理员是否能正确录入员工信息', '管理员已登录', '1. 进入员工管理模块\n2. 点击新增员工按钮\n3. 输入员工信息并保存', '员工信息录入成功并显示在员工列表中', '中', 'test', 6, 27),
    -- 12345项目 - 测试用例
    ('模块1基础功能测试', '测试模块1的基本功能是否正常', '用户已登录系统', '1. 进入模块1\n2. 执行相关操作', '模块1功能正常，无错误', '低', 'test', 7, 31),
    ('模块2高级功能测试', '测试模块2的高级功能', '用户有管理员权限', '1. 进入模块2\n2. 测试高级功能', '高级功能工作正常', '中', 'test', 7, 32);

/**
    test_bug: Bug表(14列)  无bug_id, bug_reexamine_time, bug_solved_time, bug_state
 */
INSERT INTO test_bug (bug_name, bug_content, product_manager, bug_builder, bug_receiver, bug_grade, bug_create_time, bug_project, bug_module, test_case_id)
VALUES
    -- Bug管理系统 - Bug
    ('无法创建Bug', 'Bug管理模块中无法创建新Bug，点击创建按钮无反应', 'admin', 'test', 'dev', '高', NOW(), 5, 24, 1),
    ('Bug状态更新失败', '修改Bug状态后，状态并未及时更新', 'admin', 'test', 'dev', '中', NOW(), 5, 24, 2),
    -- 酒店管理系统 - Bug
    ('预订确认信息丢失', '客房预订后，系统未显示预订确认信息', 'admin', 'test', 'dev', '高', NOW(), 6, 26, 3),
    ('员工信息无法保存', '新增员工信息后点击保存，系统提示保存失败', 'admin', 'test', 'dev', '中', NOW(), 6, 27, 4);
-- -- 12345项目 - Bug
-- INSERT INTO test_bug (bug_name, bug_content, product_manager, bug_builder, bug_receiver, bug_grade, bug_create_time, bug_project, bug_module, test_case_id)
-- VALUES
--     ('模块1崩溃问题', '在某些条件下，模块1崩溃导致系统无法继续运行', 'admin', 'tester5', 'developer5', '高', '2024-10-03 17:00:00', 7, 31, 5),
--     ('模块2功能缺失', '模块2的某些高级功能无法正常使用', 'admin', 'tester6', 'developer6', '中', '2024-10-03 17:30:00', 7, 32, 6);

/**
    user_project: 用户项目关联表
 */
INSERT INTO user_project (user_id, project_id) VALUES
-- admin
(1, 5),
(1, 6),
(1, 7),

-- pm
(4, 1),
(5, 2),
(6, 3),
(7, 4),

-- test
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 2),
(14, 2),
(15, 2),
(16, 2),
(17, 2),
(18, 3),
(19, 3),
(20, 3),
(21, 3),
(22, 3),
(23, 4),
(24, 4),
(25, 4),
(26, 4),
(27, 4),

-- dev
(28, 1),
(29, 1),
(30, 1),
(31, 1),
(32, 1),
(33, 2),
(34, 2),
(35, 2),
(36, 2),
(37, 2),
(38, 3),
(39, 3),
(40, 3),
(41, 3),
(42, 3),
(43, 4),
(44, 4),
(45, 4),
(46, 4),
(47, 4);