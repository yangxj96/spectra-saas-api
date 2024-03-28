package com.yangxj96.saas.common.respond;

import lombok.Getter;

import java.util.Objects;

/**
 * 通用响应的状态,<br>
 * </br>
 * 进行主要是进行标准化响应内容,也许以后如果增加国际化相关内容可能会有用
 */
@Getter
public enum RStatus {

    /*
     * code分布:
     * 认证相关: 100000 - 199999
     * 服务异常: 200000 - 299999
     */
    SUCCESS(0, "请求成功"),
    FAILURE(-1, "请求失败"),
    UNKNOWN(-2, "未知错误"),
    SECURITY_AUTHENTICATION(100000, "认证异常"),
    SECURITY_ACCESS_DENIED(100001, "无权限"),
    SECURITY_USERNAME_ABSENCE(100002, "用户不存在"),
    SECURITY_PASSWORD_ERROR(100003, "密码错误"),
    SECURITY_TOKEN_CLEAN(100004, "Token自动清理异常"),
    SECURITY_ACCESS_LOCKED(100004, "账号被锁定"),
    SECURITY_ACCESS_DISABLED(100004, "账号被禁用"),
    SECURITY_ACCESS_CREDENTIALS_EXPIRED(100004, "密码已过期"),
    SECURITY_ACCESS_BAD_CREDENTIALS(100004, "密码错误"),
    SECURITY_TOKEN_REFRESH(100004, "token刷新失败"),
    SECURITY_TOKEN_LOGOFF(100004, "退出登录失败"),

    GATEWAY_NOT_FOUND(200001, "未找到服务"),
    GATEWAY_RESPONSE_STATUS(200002, "网关响应状态异常"),
    NULL_POINTER(200003, "空指针异常"),
    FAILURE_INSERT(200004, "插入失败"),
    FAILURE_DELETE(200005, "删除失败"),
    FAILURE_UPDATE(200006, "更新失败"),
    FAILURE_SELECT(200007, "查询失败"),
    FAILURE_REPEAT(200008, "数据重复"),
    FAILURE_FORMAT(200009, "字段格式错误,请检查"),
    FAILURE_DATA_NULL(200010, "查询不到数据"),
    NOT_FIND_ROUTE(200011, "未找到路由"),
    NOT_FIND_TOKEN(200012, "未获取到token");

    private final Integer code;

    private final String msg;

    RStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (RStatus status : values()) {
            if (Objects.equals(status.code, code)) {
                return status.msg;
            }
        }
        return "undefined";
    }

}
