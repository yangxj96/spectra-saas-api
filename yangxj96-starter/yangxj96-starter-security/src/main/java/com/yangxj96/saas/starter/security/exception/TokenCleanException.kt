/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

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
