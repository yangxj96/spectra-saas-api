package com.yangxj96.saas.server.platform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangxj96.saas.starter.db.entity.common.QueryParams;
import com.yangxj96.saas.starter.db.entity.platform.Route;
import com.yangxj96.saas.common.base.BaseController;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.server.platform.service.RouteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 路由操作相关内容
 */
@RestController
@RequestMapping("/route")
public class RouteController extends BaseController<Route, RouteService> {

    protected RouteController(RouteService bindService) {
        super(bindService);
    }

    @PostMapping
    public Route create(@Validated @RequestBody Route params) {
        return super.create(params);
    }

    @DeleteMapping("/{id}")
    public R<Object> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @PutMapping
    public Route modify(@Validated @RequestBody Route params) {
        return super.modify(params);
    }

    @GetMapping
    public Page<Route> select(QueryParams common, Route datum) {
        return bindService.page(
                new Page<>(common.getPageNum(), common.getPageSize()),
                new LambdaQueryWrapper<>(Route.class).setEntity(datum)
        );
    }

}