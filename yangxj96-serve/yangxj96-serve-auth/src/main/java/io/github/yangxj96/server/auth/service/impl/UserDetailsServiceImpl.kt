package io.github.yangxj96.server.auth.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import io.github.yangxj96.bean.user.User
import io.github.yangxj96.server.auth.service.UserService
import jakarta.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * 用户名密码登录的实现
 */
@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Resource
    private lateinit var userService: UserService

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            if (StringUtils.isEmpty(username)) {
                throw NullPointerException("用户名为空")
            }

            val wrapper = QueryWrapper<User>()
                .eq("username", username)
                .last("LIMIT 1")

            val user = userService.getOne(wrapper) ?: throw UsernameNotFoundException("用户不存在")

            user.authorities = userService.getAuthoritiesByUserId(user.id!!)

            return user
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
