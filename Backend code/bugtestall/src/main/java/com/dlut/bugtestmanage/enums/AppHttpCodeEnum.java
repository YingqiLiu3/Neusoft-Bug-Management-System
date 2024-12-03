package com.dlut.bugtestmanage.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "Success"),
    // 错误
    SYSTEM_ERROR(500, "Error"),
    // 用户管理 6xx
    // 用户注册模块
    USER_EXISTS(600, "用户已存在"),
    USERNAME_NOT_NULL(601, "用户名不能为空"),
    PASSWORD_NOT_NULL(602, "密码不能为空"),
    ROLETYPE_NOT_NULL(603, "用户角色类型不能为空"),
    ROLETYPE_NOT_EXISTS(604, "用户角色类型不存在"),
    // 用户登录模块
    USER_NOT_EXISTS(605, "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(606, "用户名或密码错误"),
    LOGIN_EXPIRATION(607, "登录过期, 请重新登录"),
    // 用户退出模块
    USER_NOT_ONLINE(608, "用户未登录"),

    // 项目管理 7xx
    // 项目新增模块
    PROJECT_NAME_NOT_NULL(700, "项目名称不能为空"),
    PROJECT_DESCRIPTION_NOT_NULL(701, "项目描述不能为空"),
    PROJECT_CREATOR_NOT_NULL(702, "项目创建人不能为空"),
    PROJECT_CREATE_TIME_NOT_NULL(703, "项目创建时间不能为空"),
    PROJECT_CREATOR_NOT_EXIST(704, "项目创建人不存在"),
    PROJECT_CREATOR_AUTH_ERROR (705, "该项目创建人无权创建项目"),
    PROJECT_USER_RELATION_EXISTS (706, "项目用户关联已存在"),
    // 编辑项目信息
    PROJECT_NOT_EXISTS (707, "项目不存在"),
    PROJECT_CREATOR_CANNOT_MODIFY(708, "项目创建人不可修改"),
    PROJECT_CREATE_TIME_CANNOT_MODIFY(709, "项目创建创建时间不可修改"),
    // 删除项目
    PROJECT_EXISTS_BUG(710, "项目存在Bug, 不能删除"),
    PROJECT_MODULE_NAME_NOT_NULL(711,"模块名称不能为空"),
    PROJECT_MODULE_DESCRIPTION_NOT_NULL(712,"模块描述不能为空"),
    PROJECT_MODULE_NOT_EXISTS(713,"模块不存在"),
    BUG_NAME_NOT_EXISTS(714,"模块名称不存在"),
    BUG_MODULE_NOT_EXISTS(715,"BUG所属模块不存在"),
    BUG_BUILDER_NOT_EXISTS(716,"BUG创建人不存在"),
    BUG_CONTENT_NOT_EXISTS(717,"BUG内容不存在"),
    BUG_CREATOR_AUTH_ERROR(718,"BUG创建人没有权限"),
    BUG_CREATE_TIME_ERROR(719,"BUG创建时间无效"),
    BUG_SOLVE_TIME_ERROR(720,"BUG解决时间无效"),
    BUG_PROJECT_MANAGER(721,"项目负责人不对劲"),
    BUG_GRADE_ERROR(722,"BUG等级不对劲"),
    BUG_NOT_EXISTS(723,"BUG不存在"),
    RALATIONSHIP_NOT_VALID(724, "bug创建人, bug所属项目id, bug所属模块id关联关系不合法"),

    TEST_CASE_ID_NOT_NULL(725,"测试用例ID不存在"),

    TEST_CASE_NAME_NOT_NULL(726,"测试用例名称不存在"),
    TEST_PURPOSE_NOT_NULL(727,"测试目的不存在"),
    PREDICTIONS_NOT_NULL(728,"前置条件不为空"),

    TEST_STEPS_NOT_NULL(729,"测试步骤不为空"),
    EXPECTED_RESULT_NOT_NULL(730,"预期结果不为空"),
    MODULE_EXISTS_BUG(731, "模块存在Bug, 不能删除"),
    TEST_CASE_EXISTS_BUG(732, "测试用例存在Bug, 不能删除");
    int code;
    String msg;
    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
