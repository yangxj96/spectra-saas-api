/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.bean.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BasicEntity
import java.io.Serializable

/**
 * 权限表实体
 */
@TableName(value = "db_user.t_authority")
class Authority : BasicEntity(), Serializable {

    /**
     * 名称
     */
    @TableField(value = "code")
    var code: String? = null

    /**
     * 描述
     */
    @TableField(value = "description")
    var description: String? = null
}