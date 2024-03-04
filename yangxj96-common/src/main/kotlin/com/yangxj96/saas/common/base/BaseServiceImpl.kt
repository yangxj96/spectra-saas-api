package com.yangxj96.saas.common.base

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yangxj96.saas.common.exception.PlaceholderException
import org.springframework.transaction.annotation.Transactional

/**
 * RESTFul 接口公用service层
 *
 * @param <M> 子类对应的mapper
 * @param <O> 子类对应的实体
</O></M> */
open class BaseServiceImpl<M : BaseMapper<O>, O : BaseEntity>
protected constructor(protected val bindMapper: M) : ServiceImpl<M, O>(), BaseService<O> {

    @Transactional(rollbackFor = [Exception::class])
    override fun create(datum: O): O {
        return if (bindMapper.insert(datum) == 1) {
            bindMapper.selectById(datum.id)
        } else datum
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun delete(id: String): Boolean {
        val datum = getById(id.toLong()) ?: throw PlaceholderException()
        return this.removeById(datum.id)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun modify(datum: O): O {
        if (null == getById(datum.id)) {
            throw PlaceholderException()
        }
        return if (updateById(datum)) {
            getById(datum.id)
        } else datum
    }

}
