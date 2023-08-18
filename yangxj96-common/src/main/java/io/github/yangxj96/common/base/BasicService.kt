package io.github.yangxj96.common.base

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

}
