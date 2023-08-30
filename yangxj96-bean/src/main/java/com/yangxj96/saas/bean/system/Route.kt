package com.yangxj96.saas.bean.system

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BasicEntity
import java.io.Serializable

@TableName(value = "db_system.t_route")
class Route : BasicEntity(), Serializable {

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