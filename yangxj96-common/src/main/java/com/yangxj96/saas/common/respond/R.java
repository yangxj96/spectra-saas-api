package com.yangxj96.saas.common.respond;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用响应
 */
@Data
class R<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;


    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    private static R<Object> success() {
        return new R<>(RStatus.SUCCESS.getCode(), RStatus.SUCCESS.getMsg());
    }

    private static <T> R<T> success(T data) {
        return new R<>(RStatus.SUCCESS.getCode(), RStatus.SUCCESS.getMsg(), data);
    }

    private static R<Object> failure() {
        return new R<>(RStatus.FAILURE.getCode(), RStatus.FAILURE.getMsg());
    }

    private static R<Object> failure(RStatus status) {
        return new R<>(status.getCode(), status.getMsg());
    }

    private static R<Object> failure(String message) {
        return new R<>(RStatus.FAILURE.getCode(), message);
    }

    private static <T> R<T> failure(RStatus status, T data) {
        return new R<>(status.getCode(), status.getMsg(), data);
    }

}
