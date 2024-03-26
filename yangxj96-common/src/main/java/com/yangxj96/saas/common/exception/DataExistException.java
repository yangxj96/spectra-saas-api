package com.yangxj96.saas.common.exception;

public class DataExistException extends RuntimeException {

    private DataExistException(String message) {
        super(message);
    }

}