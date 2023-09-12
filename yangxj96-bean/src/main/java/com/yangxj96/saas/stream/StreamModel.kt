/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.stream

/**
 * 流消息格式
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 14:52
 */
class StreamModel {

    var type: String? = null

    var message: Any? = null
    override fun toString(): String {
        return "StreamModel(type=$type, message=$message)"
    }
}
