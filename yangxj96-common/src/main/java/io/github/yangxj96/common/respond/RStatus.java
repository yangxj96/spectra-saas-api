package io.github.yangxj96.common.respond;

import lombok.Getter;

/**
 * 通用响应的状态,<br/>
 * 进行主要是进行标准化响应内容,也许以后如果增加国际化相关内容可能会有用
 */
@Getter
public enum RStatus {

    /*
    code分布:
    认证相关: 100000 - 200000
     */

    SUCCESS(0, "success"),
    FAILURE(-1, "failure"),
    AUTHENTICATION(100000, "认证异常"),
    ACCESS_DENIED(100001, "无权限"),
    USERNAME_ABSENCE(100002, "用户不存在"),
    PASSWORD_ERROR(100003, "密码错误");

    private final Integer code;

    private final String msg;

    RStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
