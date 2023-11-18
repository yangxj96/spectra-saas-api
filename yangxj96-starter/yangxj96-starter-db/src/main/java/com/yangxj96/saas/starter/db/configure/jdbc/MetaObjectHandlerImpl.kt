/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.configure.jdbc

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.yangxj96.saas.bean.user.Account
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

/**
 * 元对象字段填充控制器
 */
class MetaObjectHandlerImpl : MetaObjectHandler {

    companion object {
        // @formatter:off
        /** 创建人  */
        private const val CREATED_USER = "createdUser"
         /** 创建时间  */
        private const val CREATED_TIME = "createdTime"
         /** 更新人  */
        private const val UPDATED_USER = "updatedUser"
         /** 更新时间  */
        private const val UPDATED_TIME = "updatedTime"
        // @formatter:on

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val currentAccountId: Long?
        /**
         * 获取当前用户ID
         *
         * @return 当前用户ID, 如果没有获取到则为0
         */
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return if (authentication != null && authentication !is AnonymousAuthenticationToken) {
                (authentication.principal as Account).id
            } else 0L
        }

    override fun insertFill(metaObject: MetaObject) {
        log.debug("[自动配置-MyBatisPlus] insert 填充字段开始")
        setFieldValByName(CREATED_USER, currentAccountId, metaObject)
        setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject)
        setFieldValByName(UPDATED_USER, currentAccountId, metaObject)
        setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject)
    }

    override fun updateFill(metaObject: MetaObject) {
        log.debug("[自动配置-MyBatisPlus] update 填充字段开始")
        setFieldValByName(UPDATED_USER, currentAccountId, metaObject)
        setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject)
    }

}
