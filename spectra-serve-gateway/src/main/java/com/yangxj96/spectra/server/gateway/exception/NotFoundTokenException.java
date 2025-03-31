package com.yangxj96.spectra.server.gateway.exception;


/**
 * 没找到token异常
 */
public class NotFoundTokenException extends RuntimeException {

    public NotFoundTokenException() {
        super("未获取到token");
    }

    public NotFoundTokenException(String message) {
        super(message);
    }
}