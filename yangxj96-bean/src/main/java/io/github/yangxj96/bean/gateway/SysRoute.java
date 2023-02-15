package io.github.yangxj96.bean.gateway;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 路由表定义
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_sys_route")
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
     * 路由ID
     */
    @TableField(value = "route_id")
    private String routeId;

    @TableField(value = "updated_time")
    private LocalDateTime updatedTime;

}