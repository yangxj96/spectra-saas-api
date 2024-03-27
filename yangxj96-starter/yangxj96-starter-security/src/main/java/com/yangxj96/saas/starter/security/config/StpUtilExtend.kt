//package com.yangxj96.saas.starter.security.bean
//
//import cn.dev33.satoken.stp.StpUtil
//import cn.hutool.core.util.StrUtil
//import cn.hutool.extra.spring.SpringUtil
//import com.kmlpkj.smart.village.api.service.OrgService
//import com.kmlpkj.smart.village.api.service.UserDetailsService
//import org.slf4j.LoggerFactory
//
//
///**
// * StpUtil扩展
// */
//object StpUtilExtend {
//
//    private val log = LoggerFactory.getLogger(this::class.java)
//
//    /**
//     * 获取当前登录用户的用户名
//     *
//     * @return 用户名
//     */
//    fun getLoginUsername(): String {
//        if (!StpUtil.isLogin()) {
//            throw RuntimeException("当前用户未登录")
//        }
//        val login = StpUtil.getLoginIdAsLong()
//        if (login == 0L) {
//            throw RuntimeException("获取当前用户ID失败")
//        }
//        val name = SpringUtil
//            .getBean(UserDetailsService::class.java)
//            .getByAccountId(login)?.name
//        if (StrUtil.isBlank(name)) {
//            throw RuntimeException("用户名称为空,请设置用户名称")
//        }
//        return name!!
//    }
//
//    /**
//     * 获取当前用户的组织机构ID
//     */
//    fun getLoginOrgId(): Long {
//        if (!StpUtil.isLogin()) {
//            throw RuntimeException("当前用户未登录")
//        }
//        val login = StpUtil.getLoginIdAsLong()
//        if (login == 0L) {
//            throw RuntimeException("获取当前用户ID失败")
//        }
//        val orgId = SpringUtil
//            .getBean(UserDetailsService::class.java)
//            .getByAccountId(login)?.orgId
//        if (orgId == null) {
//            throw RuntimeException("未设置组织机构")
//        }
//        return orgId
//    }
//
//    /**
//     * 获取当前用户的所有下级组织机构信息
//     */
//    fun getOrgsIds(): List<Long> {
//        try {
//            val id = StpUtil.getLoginIdAsLong()
//            val detailsService = SpringUtil.getBean(UserDetailsService::class.java)
//            val details = detailsService.getByAccountId(id)
//            if (details?.orgId == null) {
//                return emptyList()
//            }
//            val orgService = SpringUtil.getBean(OrgService::class.java)
//            val orgs = orgService.getChildrenIds(details.orgId!!)
//            val result = mutableListOf<Long>()
//            result.addAll(orgs)
//            result.add(details.orgId!!)
//            return result
//        } catch (e: Exception) {
//            log.atError().log("获取所属组织机构失败")
//        }
//        return emptyList()
//    }
//
//}