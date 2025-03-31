package com.yangxj96.spectra.starter.security.constant;


import lombok.Getter;

/**
 * token类型
 */

@Getter
public enum TokenStyle {

    /**
     * 格式: 32e1ed9e-3510-465f-b776-c18001e09172
     */
    UUID("uuid"),

    /**
     * 格式: 32e1ed9e3510465fb776c18001e09172
     */
    SIMPLE_UUID("simple-uuid"),

    /**
     * 格式: J8IjEquDdBrsybI0BKisnpFVPxz2plsl
     */
    RANDOM_32("random-32"),

    /**
     * 格式: 1JhIvbr208c0Jg08Y2Y8dtyKaDOD8mDxjPQi7Loq1Gz1JU4SA3Sp4lT7PNQCRqLu
     */
    RANDOM_64("random-64"),

    /**
     * 格式: IDiLCV1LkkXEjfmkzPDANajC0hxbKt5aU4qKoyrIivk39ZDe6kib4Ts0PFLQ3txhqINjAKKnD9l4E5mxBtC63OqpDL5Rk4JFMHmw8eUhxuvH2HmYuNYrWEy4XLBb3vKQ
     */
    RANDOM_128("random-128"),

    /**
     * 格式: Md_Ir1HNvGfXR9tXH_63X2xeWfbP33BVGt__
     */
    TIK("tik");

    private final String v;

    TokenStyle(String v) {
        this.v = v;
    }
}