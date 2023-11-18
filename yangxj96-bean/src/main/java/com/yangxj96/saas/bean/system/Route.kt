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

@TableName(value = "db_system.t_route")
class Route : BaseEntity(), Serializable {

    @TableField(value = "uri")
    var uri: String? = null

    @TableField(value = "\"order\"")
    var order: Int? = null

    @TableField(value = "predicates")
    var predicates: String? = null

    @TableField(value = "filters")
    var filters: String? = null

    @TableField(value = "metadata")
    var metadata: String? = null

    @TableField(value = "route_id")
    var routeId: String? = null

    override fun toString(): String {
        return "Route(uri=$uri, order=$order, predicates=$predicates, filters=$filters, metadata=$metadata, routeId=$routeId)"
    }

}