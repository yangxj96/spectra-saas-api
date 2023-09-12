/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.fallback

import com.yangxj96.saas.starter.remote.clients.SystemFeignClient


class SystemFeignClientFallback : SystemFeignClient {

    override fun get(): String {
        return "熔断1"
    }

    override fun demo2(): String {
        return "熔断2"
    }

}
