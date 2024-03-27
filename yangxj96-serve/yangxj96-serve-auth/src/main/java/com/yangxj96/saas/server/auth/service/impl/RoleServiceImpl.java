package com.yangxj96.saas.server.auth.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangxj96.saas.bean.user.Authority;
import com.yangxj96.saas.bean.user.Role;
import com.yangxj96.saas.common.base.BaseServiceImpl;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.common.respond.RStatus;
import com.yangxj96.saas.server.auth.mapper.RoleMapper;
import com.yangxj96.saas.server.auth.pojo.vo.RoleRelevance;
import com.yangxj96.saas.server.auth.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 角色service的实现
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    protected RoleServiceImpl(RoleMapper bindMapper) {
        super(bindMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Role create(Role datum) {
        if (!datum.getCode().startsWith("ROLE_")) {
            R.failure(RStatus.FAILURE_FORMAT);
            return datum;
        }
        var wrapper = new QueryWrapper<Role>()
                .eq("code", datum.getCode())
                .last("LIMIT 1");
        var db = this.getOne(wrapper);
        if (this.getOne(wrapper) != null) {
            R.failure(RStatus.FAILURE_REPEAT);
            return db;
        }
        return super.create(datum);
    }

    @Transactional(rollbackFor = Exception.class)
    public void relevance(RoleRelevance params) {

    }

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

    public List<Authority> ownerAuthority(Long id) {
        return bindMapper.ownerAuthority(id);
    }

    public List<Authority> getAuthorityByRoleCode(String roleCode) {
        var wrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getCode, roleCode)
                .last("LIMIT 1");
        var role = this.getOne(wrapper);
        if (role == null) {
            return Collections.emptyList();
        }
        return bindMapper.getAuthorityByRoleCode(role.getId());
    }
}
