package com.yangxj96.saas.starter.security.filter

import com.yangxj96.saas.starter.security.store.TokenStore
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException

/**
 * 用户请求头过滤器
 */
class UserAuthorizationFilter(authenticationManager: AuthenticationManager, private val tokenStore: TokenStore) :
    BasicAuthenticationFilter(authenticationManager) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")
        if (StringUtils.isNotEmpty(authorization)) {
            try {
                // 放入security上下文,就可以进行认证了
                SecurityContextHolder.getContext().authentication = tokenStore.read(authorization)
            } catch (e: Exception) {
                log.debug("读取认证信息错误")
            }
        }
        super.doFilterInternal(request, response, chain)
    }
}
