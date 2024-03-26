package com.yangxj96.saas.bean.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangxj96.saas.common.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由对象数据库实体
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_route")
class Route extends BaseEntity implements Serializable {

    @TableField(value = "uri")
    private String uri;

    @TableField(value = "\"order\"")
    private Integer order;

    @TableField(value = "predicates")
    private String predicates;

    @TableField(value = "filters")
    private String filters;

    @TableField(value = "metadata")
    private String metadata;

    @TableField(value = "route_id")
    private String routeId;

}