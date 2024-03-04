package com.yangxj96.saas.common.respond

import java.io.Serializable

/**
 * 通用响应
 */
class R<T> : Serializable {

    var code: Int? = null

    var msg: String? = null

    var data: T? = null

    constructor()

    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }

    constructor(code: Int, msg: String, data: T?) : this(code, msg) {
        this.data = data
    }



    companion object {

        fun success(): R<Any> {
            return R(RStatus.SUCCESS.code, RStatus.SUCCESS.msg)
        }

        fun <T> success(data: T?): R<T> {
            return R(RStatus.SUCCESS.code, RStatus.SUCCESS.msg, data)
        }

        fun failure(): R<Any> {
            return R(RStatus.FAILURE.code, RStatus.FAILURE.msg)
        }

        fun failure(status: RStatus): R<Any> {
            return R(status.code, status.msg)
        }

        fun failure(message: String): R<Any> {
            return R(RStatus.FAILURE.code, message)
        }

        fun <T> failure(status: RStatus, data: T?): R<T> {
            return R(status.code, status.msg, data)
        }
    }

    override fun toString(): String {
        return "R(code=$code, msg=$msg, data=$data)"
    }
}
