package io.github.yangxj96.server.auth.service.impl

import com.baomidou.mybatisplus.core.toolkit.IdWorker
import io.github.yangxj96.bean.user.Authority
import io.github.yangxj96.bean.user.User
import io.github.yangxj96.common.base.BasicEntity
import io.github.yangxj96.common.base.BasicServiceImpl
import io.github.yangxj96.server.auth.mapper.UserMapper
import io.github.yangxj96.server.auth.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.function.Consumer

/**
 * 用户service的实现
 */
@Service
class UserServiceImpl protected constructor(bindMapper: UserMapper) :
    BasicServiceImpl<UserMapper, User>(bindMapper), UserService {
    override fun relevance(user: Long, role: Long): Boolean {
        return bindMapper.relevance(IdWorker.getId(), user, role)
    }

    override fun getAuthoritiesByUserId(userId: Long): MutableList<GrantedAuthority> {
        val result: MutableList<GrantedAuthority> = ArrayList()
        val roles = bindMapper.getRoleByUserId(userId)
        if (roles.isNotEmpty()) {
            roles.forEach(Consumer { result.add(SimpleGrantedAuthority(it.code)) })
            val roleIds = roles.stream().map(BasicEntity::id).toList()
            val authorities = bindMapper.getAuthorityByRoleIds(roleIds)
            if (authorities.isNotEmpty()) {
                authorities.forEach(Consumer { i: Authority -> result.add(SimpleGrantedAuthority(i.code)) })
            }
        }
        return result
    }
}
