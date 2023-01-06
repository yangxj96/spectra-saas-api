package io.github.yangxj96.bean.gateway;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 路由表定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_sys_route", autoResultMap = true)
public class SysRoute extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    @TableField(value = "predicates", typeHandler = JacksonTypeHandler.class)
    private transient List<PredicateDefinition> predicates;

    /**
     * 过滤器
     */
    @TableField(value = "filters", typeHandler = JacksonTypeHandler.class)
    private transient List<FilterDefinition> filters;

    /**
     * 元数据
     */
    @TableField(value = "metadata", typeHandler = JacksonTypeHandler.class)
    private transient Map<String, Object> metadata;

    /**
     * 路由id
     */
    @TableField(value = "route_id")
    private String routeId;
}