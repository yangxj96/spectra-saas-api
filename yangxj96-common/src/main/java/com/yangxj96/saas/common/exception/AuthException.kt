package com.yangxj96.saas.common.exception

import com.yangxj96.saas.common.respond.RStatus

/**
 * 认证异常
 */
class AuthException(val status: RStatus) : RuntimeException(status.msg)