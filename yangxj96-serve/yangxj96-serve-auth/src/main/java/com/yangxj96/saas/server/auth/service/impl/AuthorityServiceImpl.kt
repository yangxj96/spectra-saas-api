package com.yangxj96.saas.server.auth.service.impl


import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.common.base.BasicServiceImpl
import com.yangxj96.saas.server.auth.mapper.AuthorityMapper
import com.yangxj96.saas.server.auth.service.AuthorityService
import org.springframework.stereotype.Service

/**
 * 权限service的实现
 */
@Service
class AuthorityServiceImpl protected constructor(bindMapper: AuthorityMapper) :
    BasicServiceImpl<AuthorityMapper, Authority>(bindMapper), AuthorityService
