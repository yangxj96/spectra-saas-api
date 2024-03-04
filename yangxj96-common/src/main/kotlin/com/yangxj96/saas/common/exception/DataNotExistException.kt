package com.yangxj96.saas.common.exception

/**
 * 数据不存在异常
 *
 * @param message 消息
 */
class DataNotExistException(message: String) : RuntimeException(message)