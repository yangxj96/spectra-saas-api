/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.bean.security

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 存储的refresh token实体
 */
@TableName(value = "db_user.t_token_refresh")
class TokenRefresh : BaseEntity(), Serializable {

    /**
     * 权限token id
     */
    @TableField(value = "access_id")
    var accessId: Long? = null

    /**
     * token
     */
    @TableField(value = "token")
    var token: String? = null

    /**
     * 到期时间
     */
    @TableField(value = "expiration_time")
    var expirationTime: LocalDateTime? = null

}
