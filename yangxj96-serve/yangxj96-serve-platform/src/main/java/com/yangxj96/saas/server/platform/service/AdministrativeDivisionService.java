package com.yangxj96.saas.server.platform.service;

import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.saas.bean.platform.AdministrativeDivision;
import com.yangxj96.saas.common.base.BaseService;

import java.util.List;

public interface AdministrativeDivisionService extends BaseService<AdministrativeDivision> {


    /**
     * 返回树形结构行政区划
     * @return 行政区划树
     */
    List<Tree<String>> tree(Long pid);

}
