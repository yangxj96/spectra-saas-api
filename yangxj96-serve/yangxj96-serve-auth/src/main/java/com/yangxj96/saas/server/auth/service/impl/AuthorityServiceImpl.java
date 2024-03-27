package com.yangxj96.saas.server.auth.service.impl;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.yangxj96.saas.bean.user.Authority;
import com.yangxj96.saas.common.base.BaseServiceImpl;
import com.yangxj96.saas.server.auth.mapper.AuthorityMapper;
import com.yangxj96.saas.server.auth.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 权限service的实现
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    protected AuthorityServiceImpl(AuthorityMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public List<Tree<String>> tree() {
        var roles = this.list();
        if (roles.isEmpty()) {
            return Collections.emptyList();
        }
        var config = new TreeNodeConfig();
        config.setIdKey("id");
        config.setParentIdKey("pid");
        config.setNameKey("name");
        config.setChildrenKey("children");
        return TreeUtil.build(roles, "0", config, (node, tree) -> {
            tree.setId(node.getId().toString());
            tree.setParentId(node.getPid().toString());
            tree.setName(node.getName());
            tree.putExtra("code", node.getCode());
            tree.putExtra("description", node.getDescription());
        });
    }

}
