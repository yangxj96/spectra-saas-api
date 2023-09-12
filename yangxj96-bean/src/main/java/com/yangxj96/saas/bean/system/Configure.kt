/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.bean.system

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable

/**
 * 系统配置表
 */
@TableName(value = "db_system.t_configure")
class Configure : BaseEntity(), Serializable {

    /**
     * key
     */
    @TableField(value = "\"key\"")
    var key: String? = null

    /**
     * value
     */
    @TableField(value = "\"value\"")
    var value: String? = null
}