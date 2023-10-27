/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.common.respond

import java.io.Serializable

/**
 * 通用响应
 */
class R(var code: Int, var msg: String) : Serializable {

    var data: Any? = null

    constructor(code: Int, msg: String, data: Any?) : this(code, msg) {
        this.data = data
    }

    companion object {

        fun success(): R {
            return R(RStatus.SUCCESS.code, RStatus.SUCCESS.msg)
        }

        fun success(data: Any?): R {
            return R(RStatus.SUCCESS.code, RStatus.SUCCESS.msg, data)
        }

        fun failure(): R {
            return R(RStatus.FAILURE.code, RStatus.FAILURE.msg)
        }

        fun failure(status: RStatus): R {
            return R(status.code, status.msg)
        }

        fun failure(message: String): R {
            return R(RStatus.FAILURE.code, message)
        }

        fun failure(status: RStatus, data: Any?): R {
            return R(status.code, status.msg, data)
        }
    }
}
