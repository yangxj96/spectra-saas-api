package com.yangxj96.saas.server.auth.dubbo;

import com.yangxj96.saas.bean.user.Authority;
import com.yangxj96.saas.server.auth.service.RoleService;
import com.yangxj96.saas.starter.dubbo.dubbo.auth.RoleDubboService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@Slf4j
@DubboService
public class RoleDubboServiceImpl implements RoleDubboService {

    @Resource
    private RoleService roleService;

    @Override
    public List<Authority> getAuthorityByRoleCode(String roleCode) {
        log.atDebug().log("dubbo,根据角色ID获取角色所拥有的权限");
        return roleService.getAuthorityByRoleCode(roleCode);
    }

}