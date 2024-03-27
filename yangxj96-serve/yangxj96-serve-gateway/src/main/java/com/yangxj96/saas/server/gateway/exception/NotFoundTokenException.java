package com.yangxj96.saas.server.gateway.exception


/**
 * 没找到token异常
 */
class NotFoundTokenException extends RuntimeException {

    public NotFoundTokenException() {
        super("未获取到token");
    }

    public NotFoundTokenException(String message) {
        super(message);
    }
}