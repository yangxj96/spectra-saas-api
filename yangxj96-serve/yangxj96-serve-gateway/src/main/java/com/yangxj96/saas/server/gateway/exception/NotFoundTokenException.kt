/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-30 15:02:51
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.server.gateway.exception


/**
 * 没找到token异常
 */
class NotFoundTokenException(override val message: String?) : RuntimeException("未获取到token") {

}