package io.github.yangxj96.common.respond;

import lombok.Getter;

import java.util.Objects;

/**
 * 通用响应的状态,<br/>
 * 进行主要是进行标准化响应内容,也许以后如果增加国际化相关内容可能会有用
 */
@Getter
public enum RStatus {

    /*
    code分布:
    认证相关: 100000 - 199999
    服务异常: 200000 - 299999
     */

    SUCCESS(0, "success"),
    FAILURE(-1, "failure"),
    AUTHENTICATION(100000, "认证异常"),
    ACCESS_DENIED(100001, "无权限"),
    USERNAME_ABSENCE(100002, "用户不存在"),
    PASSWORD_ERROR(100003, "密码错误"),
    GATEWAY_NOT_FOUND(200001, "未找到服务"),
    GATEWAY_RESPONSE_STATUS(200002, "网关响应状态异常"),
    NULL_POINTER(200003, "空指针异常"),
    FAILURE_INSERT(200004, "插入失败"),
    FAILURE_DELETE(200005, "删除失败"),
    FAILURE_UPDATE(200006, "更新失败"),
    FAILURE_SELECT(200007, "查询失败"),
    NOT_FIND_ROUTE(200008, "未找到路由");

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
