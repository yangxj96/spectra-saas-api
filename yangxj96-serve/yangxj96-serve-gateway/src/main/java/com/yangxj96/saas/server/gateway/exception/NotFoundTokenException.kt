package com.yangxj96.saas.server.gateway.exception


/**
 * 没找到token异常
 */
class NotFoundTokenException(override val message: String?) : RuntimeException("未获取到token")