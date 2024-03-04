package com.yangxj96.saas.common.base

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

}
