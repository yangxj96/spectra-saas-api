package com.yangxj96.saas.starter.security.evaluator

import com.yangxj96.saas.starter.security.constant.EnvCons
import org.slf4j.LoggerFactory
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.util.AntPathMatcher
import java.io.Serializable


/**
 * 通用权限匹配
 *
 * @author yangxj96
 */
class WildcardPermissionEvaluator : PermissionEvaluator {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val matcher = AntPathMatcher()

    override fun hasPermission(authentication: Authentication, targetDomainObject: Any?, permission: Any?): Boolean {
        log.atDebug().log("${EnvCons.PREFIX} 开始匹配权限")
        val authorities = authentication.authorities
        authorities?.forEach {
            if (matcher.match(it.authority, permission as String)) {
                log.atDebug().log("${EnvCons.PREFIX} 匹配成功,有权限")
                return true
            }
        }
        log.atDebug().log("${EnvCons.PREFIX} 匹配失败,无权限")
        return false
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        return false
    }
}