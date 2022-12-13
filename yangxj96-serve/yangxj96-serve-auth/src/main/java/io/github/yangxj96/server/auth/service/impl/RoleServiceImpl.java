package io.github.yangxj96.server.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.auth.mapper.RoleMapper;
import io.github.yangxj96.server.auth.service.RoleService;
import org.springframework.stereotype.Service;

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
    public boolean relevance(Long role,Long authority) {
        return bindMapper.relevance(IdWorker.getId(),role,authority) == 1;
    }
}
