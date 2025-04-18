package com.yangxj96.spectra.starter.security.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.yangxj96.spectra.starter.db.entity.user.Authority;
import com.yangxj96.spectra.starter.db.entity.user.Role;
import com.yangxj96.spectra.starter.dubbo.dubbo.auth.AccountDubboService;
import com.yangxj96.spectra.starter.dubbo.dubbo.auth.RoleDubboService;
import com.yangxj96.spectra.starter.security.constant.EnvCons;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.ArrayList;
import java.util.List;


/**
 * 权限数据加载源接口
 */
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @DubboReference
    private RoleDubboService roleDubboService;

    @DubboReference
    private AccountDubboService accountDubboService;

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    public List<String> getPermissionList(Object loginId, String loginType) {
        log.atDebug().log("{} satoken获取权限列表", EnvCons.PREFIX);
        var authorityList = new ArrayList<String>();
        var roleList = getRoleList(loginId, loginType);
        roleList.forEach(it -> {
            var ps = roleDubboService.getAuthorityByRoleCode(it);
            if (CollUtil.isNotEmpty(ps)) {
                authorityList.addAll(ps.stream().map(Authority::getCode).toList());
            }
        });
        return authorityList;
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    public List<String> getRoleList(Object loginId, String loginType) {
        log.atDebug().log("{} satoken获取角色列表", EnvCons.PREFIX);
        var roles = accountDubboService.getRoles(Long.parseLong(loginId.toString()));
        return roles.stream().map(Role::getCode).toList();
    }
}