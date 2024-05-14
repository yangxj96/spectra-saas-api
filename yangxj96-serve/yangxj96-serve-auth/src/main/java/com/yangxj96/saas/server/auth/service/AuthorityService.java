package com.yangxj96.saas.server.auth.service;

import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.saas.starter.db.entity.user.Authority;
import com.yangxj96.saas.common.base.BaseService;

import java.util.List;


/**
 * 权限service层
 */
public interface AuthorityService extends BaseService<Authority> {

    /**
     * 返回树格式的权限列表
     *
     * @return 树结构的权限列表
     */
    List<Tree<String>> tree();
}
