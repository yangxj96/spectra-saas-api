package com.yangxj96.saas.starter.db.entity.platform;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangxj96.saas.common.base.BaseEntity;
import com.yangxj96.saas.starter.db.handlers.JacksonArrayTypeHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由对象数据库实体
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_route", autoResultMap = true)
public class Route extends BaseEntity {

    @TableField(value = "uri")
    private String uri;

    @TableField(value = "\"order\"")
    private Integer order;

    @TableField(value = "predicates", typeHandler = JacksonArrayTypeHandler.class)
    private String predicates;

    @TableField(value = "filters", typeHandler = JacksonArrayTypeHandler.class)
    private String filters;

    @TableField(value = "metadata", typeHandler = JacksonArrayTypeHandler.class)
    private String metadata;

    @TableField(value = "route_id")
    private String routeId;

}