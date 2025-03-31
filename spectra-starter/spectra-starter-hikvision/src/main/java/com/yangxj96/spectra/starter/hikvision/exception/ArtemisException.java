package com.yangxj96.spectra.starter.hikvision.exception;

/**
 * 海康相关异常
 */
public class ArtemisException extends RuntimeException {

    public ArtemisException() {
        super();
    }

    public ArtemisException(String message) {
        super(message);
    }

    public ArtemisException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtemisException(Throwable cause) {
        super(cause);
    }
}
