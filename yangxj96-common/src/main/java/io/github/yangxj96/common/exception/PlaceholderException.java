package io.github.yangxj96.common.exception;

/**
 * 占位异常
 *
 * @author yangxj96
 */
public class PlaceholderException extends RuntimeException {

    public PlaceholderException() {
        super("仅在占位的时候使用");
    }

}