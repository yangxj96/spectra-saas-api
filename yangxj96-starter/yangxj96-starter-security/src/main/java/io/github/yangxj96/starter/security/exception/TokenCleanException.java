/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.security.exception;

import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.common.respond.RStatus;

/**
 * token 清理异常
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public class TokenCleanException extends SecurityException {

    public TokenCleanException() {
        super();
        R.specify(RStatus.SECURITY_TOKEN_CLEAN);
    }

    public TokenCleanException(String message) {
        super(message);
        R.specify(RStatus.SECURITY_TOKEN_CLEAN);
    }

    public TokenCleanException(String message, Throwable cause) {
        super(message, cause);
        R.specify(RStatus.SECURITY_TOKEN_CLEAN);
    }

    public TokenCleanException(Throwable cause) {
        super(cause);
        R.specify(RStatus.SECURITY_TOKEN_CLEAN);
    }

    protected TokenCleanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        R.specify(RStatus.SECURITY_TOKEN_CLEAN);
    }
}
