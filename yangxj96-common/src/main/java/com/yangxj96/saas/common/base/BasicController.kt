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
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * RESTFul 接口公用Controller层
 *
 * @param <O> 入参对象,可以为数据库po对象或者是自定义的dto对象
 * @param <S> 相关的service层,需要继承BasicService
</S></O> */
@Validated
abstract class BasicController<O : BasicEntity, S : BasicService<O>> protected constructor(protected var bindService: S) {

    /**
     * 基础创建数据接口
     *
     * @param obj 数据实体
     * @return 创建后的数据实体
     */
    @PostMapping
    fun create(@Validated obj: O): O {
        return bindService.create(obj)
    }

    /**
     * 基础删除接口
     *
     * @param id 数据id
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: @NotBlank(message = "需要删除的资源id不能为空") String) {
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
    @PutMapping
    fun modify(@Validated obj: O): O {
        return bindService.modify(obj)
    }

    /**
     * 根据实体属性分页查询数据
     *
     * @param obj 查询对象
     * @param pageNum 页码
     * @param pageSize 每页数量
     */
    @GetMapping
    fun query(
        obj: O,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<O> {
        return bindService.select(obj, pageNum, pageSize)
    }

    /**
     * 根据ID查询数据
     *
     * @param id 数据的ID
     */
    @GetMapping("/byId/{id}")
    fun getById(@PathVariable id: Long): O {
        return bindService.getById(id)
    }

}
