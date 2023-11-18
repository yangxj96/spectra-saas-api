package com.yangxj96.saas.common.base

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yangxj96.saas.common.exception.PlaceholderException
import org.bouncycastle.asn1.x500.style.RFC4519Style.o
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

    @Transactional(rollbackFor = [Exception::class])
    override fun page(datum: O, pageNum: Long, pageSize: Long): IPage<O> {
        val wrapper = QueryWrapper<O>()
            .setEntity(datum)
            .orderByDesc("created_time")
        return this.page(Page(pageNum, pageSize), wrapper)
    }
}
