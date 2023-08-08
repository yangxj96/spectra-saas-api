package io.github.yangxj96.bean.gateway

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.github.yangxj96.common.base.BasicEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 路由表定义
 */
@TableName(value = "db_system.t_sys_route")
class SysRoute : BasicEntity(), Serializable {

    /**
     * 路由地址
     */
    @TableField(value = "uri")
    var uri: String? = null

    /**
     * 排序
     */
    @TableField(value = "\"order\"")
    var order: Int? = null

    /**
     * 断言
     */
    @TableField(value = "predicates")
    var predicates: String? = null

    /**
     * 过滤器
     */
    @TableField(value = "filters")
    var filters: String? = null

    /**
     * 元数据
     */
    @TableField(value = "metadata")
    var metadata: String? = null

    /**
     * 路由ID
     */
    @TableField(value = "route_id")
    var routeId: String? = null

    @TableField(value = "updated_time")
    override var updatedTime: LocalDateTime? = null
}