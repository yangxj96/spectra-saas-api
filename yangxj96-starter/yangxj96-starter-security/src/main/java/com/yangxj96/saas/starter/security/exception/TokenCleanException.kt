package com.yangxj96.saas.starter.security.exception

import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RStatus


/**
 * token 清理异常
 */
class TokenCleanException : SecurityException {
    constructor() : super() {
        R.failure(RStatus.SECURITY_TOKEN_CLEAN)
    }

    constructor(message: String?) : super(message) {
        R.failure(RStatus.SECURITY_TOKEN_CLEAN)
    }

    constructor(message: String?, cause: Throwable?) : super(message, cause) {
        R.failure(RStatus.SECURITY_TOKEN_CLEAN)
    }

    constructor(cause: Throwable?) : super(cause) {
        R.failure(RStatus.SECURITY_TOKEN_CLEAN)
    }

    protected constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace) {
        R.failure(RStatus.SECURITY_TOKEN_CLEAN)
    }
}
