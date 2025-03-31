package com.yangxj96.spectra.common.exception;

/**
 * 占位异常
 */
public class PlaceholderException extends RuntimeException {

    public PlaceholderException() {
        super("仅在占位的时候使用");
    }

}