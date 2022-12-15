package io.github.yangxj96.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 字典类型
 *
 * @author yangxj96
 */
@Getter
public enum DictionariesType {

    GROUP(1,"字典组"),
    ITEM(2,"字典项");

    @EnumValue
    private final int code;

    private final String name;

    DictionariesType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
