package com.yangxj96.saas.common.base

import com.yangxj96.saas.common.respond.R
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated

/**
 * RESTFul 接口公用Controller层
 *
 * @param <O> 入参对象,可以为数据库po对象或者是自定义的dto对象
 * @param <S> 相关的service层,需要继承BasicService
</S></O> */
@Validated
abstract class BaseController<O : BaseEntity, S : BaseService<O>> protected constructor(protected var bindService: S) {

    protected val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 基础创建数据接口
     *
     * @param params 数据实体
     * @return 创建后的数据实体
     */
    fun create(params: O): O {
        log.atDebug().log("数据新增,数据实体:{}", params)
        return bindService.create(params)
    }

    /**
     * 基础删除接口
     *
     * @param id 数据id
     */
    fun delete(id: String) {
        log.atDebug().log("数据删除,数据ID:{}", id)
        if (bindService.delete(id)) {
            R.success()
        } else {
            R.failure()
        }
    }

    /**
     * 基础修改接口
     *
     * @param params 数据实体
     * @return 修改成功返回修改后的数据, 否则返回null
     */
    fun modify(params: O): O {
        log.atDebug().log("数据修改,数据实体:{}", params)
        return bindService.modify(params)
    }

}
