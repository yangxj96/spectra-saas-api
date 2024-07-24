package com.yangxj96.saas.starter.hikvision.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 回放响应
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CameraPlayback {

    /**
     * 录像片段信息
     */
    private List<Fragment> list;

    /**
     * 分页标记 标记本次查询的全部标识符，用于查询分片时的多次查询
     */
    private String uuid;

    /**
     * 取流短url，注：rtsp的回放url后面要指定?playBackMode=1 在vlc上才能播放
     */
    private String url;


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Fragment {

        /**
         * 查询录像的锁定类型，0-全部录像；1-未锁定录像；2-已锁定录像。
         */
        private Integer lockType;

        /**
         * 开始时间 录像片段的开始时间
         */
        private LocalDateTime beginTime;

        /**
         * 结束时间 录像片段的开始时间
         */
        private LocalDateTime endTime;

        /**
         * 录像片段大小 单位byte
         */
        private Long size;
    }

}
