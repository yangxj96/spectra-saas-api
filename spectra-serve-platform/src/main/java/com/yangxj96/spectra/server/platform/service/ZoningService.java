package com.yangxj96.spectra.server.platform.service;

import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.spectra.starter.db.entity.platform.Zoning;
import com.yangxj96.spectra.common.base.BaseService;

import java.util.List;

public interface ZoningService extends BaseService<Zoning> {


    /**
     * 返回树形结构行政区划
     * @return 行政区划树
     */
    List<Tree<String>> tree(Long pid);

}
