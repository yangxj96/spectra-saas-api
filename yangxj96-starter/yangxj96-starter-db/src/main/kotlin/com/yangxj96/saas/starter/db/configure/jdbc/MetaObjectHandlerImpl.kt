package com.yangxj96.saas.starter.db.configure.jdbc

import cn.dev33.satoken.stp.StpUtil
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
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


    override fun insertFill(metaObject: MetaObject) {
        log.atDebug().log("[MyBatisPlus] 新增填充字段")
        val id = try {
            StpUtil.getLoginIdAsLong()
        } catch (e: Exception) {
            0L
        }

        if (getFieldValByName(CREATED_USER, metaObject) == null) {
            setFieldValByName(CREATED_USER, id, metaObject)
        }
        if (getFieldValByName(CREATED_TIME, metaObject) == null) {
            setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject)
        }
        if (getFieldValByName(UPDATED_USER, metaObject) == null) {
            setFieldValByName(UPDATED_USER, id, metaObject)
        }
        if (getFieldValByName(UPDATED_TIME, metaObject) == null) {
            setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject)
        }
    }

    override fun updateFill(metaObject: MetaObject) {
        log.atDebug().log("[MyBatisPlus] 修改填充字段")
        val id = try {
            StpUtil.getLoginIdAsLong()
        } catch (e: Exception) {
            0L
        }
        setFieldValByName(UPDATED_USER, id, metaObject)
        setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject)
    }

}
