package io.github.yangxj96.bean.gateway;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由表定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_sys_route")
public class SysRoute extends BasicEntity implements Serializable {

    /**
     * 路由地址
     */
    @TableField(value = "uri")
    private String uri;

    /**
     * 排序
     */
    @TableField(value = "\"order\"")
    private Integer order;

    /**
     * 断言
     */
    @TableField(value = "predicates")
    private String predicates;

    /**
     * 过滤器
     */
    @TableField(value = "filters")
    private String filters;

    /**
     * 元数据
     */
    @TableField(value = "metadata")
    private String metadata;

    /**
     * 路由id
     */
    @TableField(value = "route_id")
    private String routeId;
}