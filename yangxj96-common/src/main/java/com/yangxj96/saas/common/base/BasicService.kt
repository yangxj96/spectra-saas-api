package com.yangxj96.saas.common.base

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService

/**
 * RESTFul 接口公用service层
 *
 * @param <O> 子类对应的实体
</O> */
interface BasicService<O : BasicEntity> : IService<O> {

    fun create(datum: O): O

    fun delete(id: String): Boolean

    fun modify(datum: O): O

    fun select(datum: O, pageNum: Long, pageSize: Long): IPage<O>

}
