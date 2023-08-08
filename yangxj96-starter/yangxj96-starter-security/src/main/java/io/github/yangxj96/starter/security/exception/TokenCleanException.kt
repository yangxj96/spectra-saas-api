package io.github.yangxj96.starter.security.exception

import io.github.yangxj96.common.respond.R.Companion.specify
import io.github.yangxj96.common.respond.RStatus

/**
 * token 清理异常
 */
class TokenCleanException : SecurityException {
    constructor() : super() {
        specify(RStatus.SECURITY_TOKEN_CLEAN)
    }

    constructor(message: String?) : super(message) {
        specify(RStatus.SECURITY_TOKEN_CLEAN)
    }

    constructor(message: String?, cause: Throwable?) : super(message, cause) {
        specify(RStatus.SECURITY_TOKEN_CLEAN)
    }

    constructor(cause: Throwable?) : super(cause) {
        specify(RStatus.SECURITY_TOKEN_CLEAN)
    }

    protected constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace) {
        specify(RStatus.SECURITY_TOKEN_CLEAN)
    }
}
