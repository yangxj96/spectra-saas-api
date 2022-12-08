package io.github.yangxj96.common.respond;

import lombok.Getter;

@Getter
public enum RStatus {

    SUCCESS(0, "success"),
    FAILURE(-1, "failure");

    private final Integer code;

    private final String msg;

    RStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
