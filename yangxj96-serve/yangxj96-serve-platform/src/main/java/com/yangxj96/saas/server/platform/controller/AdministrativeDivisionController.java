package com.yangxj96.saas.server.platform.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangxj96.saas.starter.db.entity.common.QueryParams;
import com.yangxj96.saas.starter.db.entity.platform.AdministrativeDivision;
import com.yangxj96.saas.common.base.BaseController;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.server.platform.service.AdministrativeDivisionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行政区划接口
 */
@RestController
@RequestMapping("/administrativeDivision")
public class AdministrativeDivisionController extends BaseController<AdministrativeDivision, AdministrativeDivisionService> {

    protected AdministrativeDivisionController(AdministrativeDivisionService bindService) {
        super(bindService);
    }

    @Override
    @PostMapping
    public AdministrativeDivision create(AdministrativeDivision params) {
        return super.create(params);
    }

    @Override
    @DeleteMapping("/{id}")
    public R<Object> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping
    public AdministrativeDivision modify(AdministrativeDivision params) {
        return super.modify(params);
    }

    @GetMapping("/page")
    public Page<AdministrativeDivision> page(QueryParams common, AdministrativeDivision datum) {
        return bindService.page(
                new Page<>(common.getPageNum(), common.getPageSize()),
                new LambdaQueryWrapper<>(AdministrativeDivision.class).setEntity(datum)
        );
    }

    @GetMapping("/tree")
    public List<Tree<String>> tree(@RequestParam(defaultValue = "0") Long pid) {
        return bindService.tree(pid);
    }
}
