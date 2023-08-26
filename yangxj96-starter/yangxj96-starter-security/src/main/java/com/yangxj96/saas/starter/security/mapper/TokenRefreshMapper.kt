/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-05-05 09:49:48
 *  Copyright (c) 2021 - 2023
 */
package com.yangxj96.saas.starter.security.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yangxj96.saas.bean.security.TokenRefresh

/**
 * Token的JDBC相关操作
 */
interface TokenRefreshMapper : BaseMapper<TokenRefresh>
