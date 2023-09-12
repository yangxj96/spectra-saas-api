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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yangxj96.saas.common.exception.PlaceholderException

/**
 * RESTFul 接口公用service层
 *
 * @param <M> 子类对应的mapper
 * @param <O> 子类对应的实体
</O></M> */
open class BasicServiceImpl<M : BasicMapper<O>, O : BasicEntity>
protected constructor(protected val bindMapper: M) : ServiceImpl<M, O>(), BasicService<O> {

    override fun create(datum: O): O {
        return if (bindMapper.insert(datum) == 1) {
            bindMapper.selectById(datum.id)
        } else datum
    }

    override fun delete(id: String): Boolean {
        val datum = getById(id.toLong()) ?: throw PlaceholderException()
        return this.removeById(datum.id)
    }

    override fun modify(datum: O): O {
        if (null == getById(datum.id)) {
            throw PlaceholderException()
        }
        return if (updateById(datum)) {
            getById(datum.id)
        } else datum
    }

    override fun select(datum: O, pageNum: Long, pageSize: Long): IPage<O> {
        val wrapper = this.query()
            .setEntity(datum)
            .page(Page(pageNum, pageSize))
        return this.page(wrapper)
    }
}
