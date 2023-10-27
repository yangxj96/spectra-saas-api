/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.common.base

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService

/**
 * RESTFul 接口公用service层
 *
 * @param <O> 子类对应的实体
</O> */
interface BaseService<O : BaseEntity> : IService<O> {

    fun create(datum: O): O

    fun delete(id: String): Boolean

    fun modify(datum: O): O

    fun page(datum: O, pageNum: Long, pageSize: Long): IPage<O>

}
