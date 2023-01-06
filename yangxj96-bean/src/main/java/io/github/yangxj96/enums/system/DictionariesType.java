/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:07:12
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 字典类型
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Getter
public enum DictionariesType {

    GROUP(1, "字典组"),
    ITEM(2, "字典项");

    @EnumValue
    private final int code;

    @JsonValue
    private final String name;

    DictionariesType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
