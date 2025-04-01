package com.yangxj96.spectra.server.auth.configuration.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.yangxj96.spectra.server.auth.service.RoleService;
import com.yangxj96.spectra.starter.db.entity.user.Authority;
import com.yangxj96.spectra.starter.db.entity.user.Role;
import com.yangxj96.spectra.server.auth.service.AccountService;
import com.yangxj96.spectra.starter.security.constant.EnvCons;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取权限使用
 */
@Slf4j
@Primary
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RoleService roleService;

    @Resource
    private AccountService accountService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        log.atDebug().log("{} sa-token获取权限列表", EnvCons.PREFIX);
        var authorityList = new ArrayList<String>();
        var roleList = getRoleList(loginId, loginType);
        roleList.forEach(it -> {
            var ps = roleService.getAuthorityByRoleCode(it);
            if (CollUtil.isNotEmpty(ps)) {
                authorityList.addAll(ps.stream().map(Authority::getCode).toList());
            }
        });
        return authorityList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        log.atDebug().log("{} sa-token获取角色列表", EnvCons.PREFIX);
        var roles = accountService.getRoles(Long.parseLong(loginId.toString()));
        return roles.stream().map(Role::getCode).toList();
    }
}