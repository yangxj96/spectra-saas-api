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
import com.yangxj96.saas.common.respond.R
import org.springframework.validation.annotation.Validated

/**
 * RESTFul 接口公用Controller层
 *
 * @param <O> 入参对象,可以为数据库po对象或者是自定义的dto对象
 * @param <S> 相关的service层,需要继承BasicService
</S></O> */
@Validated
abstract class BaseController<O : BaseEntity, S : BaseService<O>> protected constructor(protected var bindService: S) {

    /**
     * 基础创建数据接口
     *
     * @param obj 数据实体
     * @return 创建后的数据实体
     */
    fun create(obj: O): O {
        return bindService.create(obj)
    }

    /**
     * 基础删除接口
     *
     * @param id 数据id
     */
    fun delete(id: String) {
        if (bindService.delete(id)) {
            R.success()
        } else {
            R.failure()
        }
    }

    /**
     * 基础修改接口
     *
     * @param obj 数据实体
     * @return 修改成功返回修改后的数据, 否则返回null
     */
    fun modify(obj: O): O {
        return bindService.modify(obj)
    }

    /**
     * 根据实体属性分页查询数据
     *
     * @param obj 查询对象
     * @param pageNum 页码
     * @param pageSize 每页数量
     */
    fun page(obj: O, pageNum: Long, pageSize: Long): IPage<O> {
        return bindService.page(obj, pageNum, pageSize)
    }

    /**
     * 根据ID查询数据
     *
     * @param id 数据的ID
     */
    fun getById(id: Long): O {
        return bindService.getById(id)
    }

}
