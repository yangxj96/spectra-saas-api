package com.yangxj96.saas.common.exception;

/**
 * 数据不存在异常
 */
public class DataNotExistException extends RuntimeException {

    public DataNotExistException(String message) {
        super(message);
    }

}