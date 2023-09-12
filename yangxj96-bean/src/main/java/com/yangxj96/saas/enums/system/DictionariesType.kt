/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.enums.system

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.annotation.JsonValue

/**
 * 字典类型
 */
enum class DictionariesType(@EnumValue val code: Int, @JsonValue val type: String) {
    GROUP(1, "字典组"),
    ITEM(2, "字典项")
}
