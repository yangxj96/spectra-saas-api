package com.yangxj96.saas.server.platform.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.yangxj96.saas.starter.db.entity.platform.Zoning;
import com.yangxj96.saas.common.base.BaseServiceImpl;
import com.yangxj96.saas.server.platform.mapper.ZoningMapper;
import com.yangxj96.saas.server.platform.service.ZoningService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ZoningServiceImpl extends BaseServiceImpl<ZoningMapper, Zoning> implements ZoningService {

    protected ZoningServiceImpl(ZoningMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public List<Tree<String>> tree(Long pid) {
        var coll = this.list();
        if (coll == null) {
            return Collections.emptyList();
        }
        var config = new TreeNodeConfig();
        config.setIdKey("id");
        config.setParentIdKey("pid");
        config.setNameKey("name");
        config.setChildrenKey("children");
        return TreeUtil.build(coll, pid.toString(), config, (node, tree) -> {
            tree.setId(node.getId().toString());
            tree.setParentId(node.getPid().toString());
            tree.setName(node.getName());
            tree.putExtra("level", node.getLevel());
        });
    }
}
