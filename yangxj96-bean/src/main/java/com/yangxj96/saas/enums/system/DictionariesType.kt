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
