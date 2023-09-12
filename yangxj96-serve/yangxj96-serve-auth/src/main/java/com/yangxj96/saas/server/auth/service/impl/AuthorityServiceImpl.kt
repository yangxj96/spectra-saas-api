/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

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
