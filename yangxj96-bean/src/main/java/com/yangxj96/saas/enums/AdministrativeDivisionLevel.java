package com.yangxj96.saas.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 行政区划等级
 */
@Getter
public enum AdministrativeDivisionLevel {

    PROVINCE(0, "省级"),

    CITY(1, "地级"),

    COUNTY(2, "县级"),

    TOWNSHIP(3, "乡级");

    @EnumValue
    private final int code;

    @JsonValue
    private final String name;

    AdministrativeDivisionLevel(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
