package com.yangxj96.spectra.server.platform.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangxj96.spectra.starter.db.entity.common.QueryParams;
import com.yangxj96.spectra.starter.db.entity.platform.Zoning;
import com.yangxj96.spectra.common.base.BaseController;
import com.yangxj96.spectra.common.respond.R;
import com.yangxj96.spectra.server.platform.service.ZoningService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行政区划接口
 */
@RestController
@RequestMapping("/zoning")
public class ZoningController extends BaseController<Zoning, ZoningService> {

    protected ZoningController(ZoningService bindService) {
        super(bindService);
    }

    @Override
    @PostMapping
    public Zoning create(Zoning params) {
        return super.create(params);
    }

    @Override
    @DeleteMapping("/{id}")
    public R<Object> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping
    public Zoning modify(Zoning params) {
        return super.modify(params);
    }

    @GetMapping("/page")
    public Page<Zoning> page(QueryParams common, Zoning datum) {
        return bindService.page(
                new Page<>(common.getPageNum(), common.getPageSize()),
                new LambdaQueryWrapper<>(Zoning.class).setEntity(datum)
        );
    }

    @GetMapping("/tree")
    public List<Tree<String>> tree(@RequestParam(defaultValue = "0") Long pid) {
        return bindService.tree(pid);
    }
}
