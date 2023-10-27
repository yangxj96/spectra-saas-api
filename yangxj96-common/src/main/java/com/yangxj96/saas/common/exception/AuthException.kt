/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-28 01:37:09
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.common.exception

import com.yangxj96.saas.common.respond.RStatus

/**
 * 认证异常
 */
class AuthException(val status: RStatus) : RuntimeException(status.msg)