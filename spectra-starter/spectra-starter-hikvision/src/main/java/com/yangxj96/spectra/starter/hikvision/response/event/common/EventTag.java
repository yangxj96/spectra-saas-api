package com.yangxj96.spectra.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 标记
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventTag {
    private String name;
    private String value;
}
