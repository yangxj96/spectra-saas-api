/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.common.respond.RStatus;
import io.github.yangxj96.server.auth.mapper.RoleMapper;
import io.github.yangxj96.server.auth.service.RoleService;
import io.github.yangxj96.vo.RoleTree;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色service的实现
 *
 * @author yangxj96
 */
@Service
public class RoleServiceImpl extends BasicServiceImpl<RoleMapper, Role> implements RoleService {

    protected RoleServiceImpl(RoleMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public boolean relevance(Long role, Long authority) {
        return bindMapper.relevance(IdWorker.getId(), role, authority) == 1;
    }

    @Override
    public Role create(Role datum) {
        if (!datum.getCode().startsWith("ROLE_")) {
            R.failure(RStatus.FAILURE_FORMAT);
            return datum;
        }
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Role::getName, datum.getName())
                .last("LIMIT 1")
        ;
        Role db = this.getOne(wrapper);
        if (this.getOne(wrapper) != null) {
            R.failure(RStatus.FAILURE_REPEAT);
            return db;
        }
        return super.create(datum);
    }

    @Override
    public List<Tree<String>> tree() {
        List<Role> roles = this.list();

        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("id");
        config.setParentIdKey("pid");
        config.setNameKey("name");
        config.setChildrenKey("children");

        return TreeUtil.build(roles, "0", config, (node, tree) -> {
            tree.setId(node.getId().toString());
            tree.setParentId(node.getPid().toString());
            tree.setName(node.getName());
            tree.putExtra("code",node.getCode());
            tree.putExtra("description",node.getDescription());
        });
    }
}
