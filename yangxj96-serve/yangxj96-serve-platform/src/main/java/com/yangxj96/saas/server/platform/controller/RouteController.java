package com.yangxj96.saas.server.platform.controller;

import com.yangxj96.saas.bean.system.Route;
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

}