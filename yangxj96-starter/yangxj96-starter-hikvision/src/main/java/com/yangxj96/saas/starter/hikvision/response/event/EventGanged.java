package com.yangxj96.saas.starter.hikvision.response.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 联动事件响应结果
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventGanged {

    /**
     * 事件id
     */
    private String id;

    /**
     * 事件规则id
     */
    private String eventRuleId;

    /**
     * 事件规则名称
     */
    private String ruleName;

    /**
     * 事件开始时间，ISO8601格式：yyyy-MM-ddTHH:mm:ss.zzz+当前时区，例如北京时间：2018-07-26T15:00:00.000+08:00
     */
    private String startTime;

    /**
     * 事件结束时间，ISO8601格式：yyyy-MM-ddTHH:mm:ss.zzz+当前时区，例如北京时间：2018-07-26T15:00:00.000+08:00
     */
    private String endTime;

    /**
     * 事件等级
     * <ul>
     *     <li>1-低</li>
     *     <li>2-中</li>
     *     <li>3-高</li>
     * </ul>
     */
    private Integer eventLevel;

    /**
     * 事件等级value，低、中、高
     */
    private String eventLevelValue;

    /**
     * 事件等级颜色，RGB格式，例如#AE8D06
     */
    private String eventLevelColor;

    /**
     * 事件类型名称
     */
    private String eventTypeName;

    /**
     * 事件处理意见
     */
    private String remark;

    /**
     * 事件处理状态
     * <ul>
     *     <li>0-未处理</li>
     *     <li>1-已处理</li>
     * </ul>
     */
    private String handleStatus;

    /**
     * 事件记录列表
     */
    private List<LogSrc> eventLogSrcList;

    /**
     * 事件记录响应
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogSrc {

        /**
         * 事件源id
         */
        private String id;

        /**
         * 事件id
         */
        private String eventLogId;

        /**
         * 事件分类 详见附录A.62
         *
         * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#f1c62a5b">附录A.62</a>
         */
        private String ability;

        /**
         * 事件类型 参考附录D.1，附录D
         *
         * @see <a href="https:// open. hikvision. com/ docs/ docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95D%20%E4%BA%8B%E4%BB%B6%E5%88%97%E8%A1%A8-%E9%99%84%E5%BD%95D1%20%E8%A7%86%E9%A2%91%E4%BA%8B%E4%BB%B6">附录D1 视频事件</a>
         */
        private String eventType;

        /**
         * 事件类型名称
         */
        private String eventTypeName;

        /**
         * 事件源编号
         */
        private String resIndexCode;

        /**
         * 事件源类型 详见附录A.63
         *
         * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#f3db5bf5">附录A.63</a>
         */
        private String resType;

        /**
         * 事件源名称
         */
        private String resName;

        /**
         * 所属区域编号
         */
        private String regionIndexCode;

        /**
         * 所属区域名称
         */
        private String regionName;

        /**
         * 所属位置 详见附录A.81 安装位置
         *
         * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8">附录A.81 安装位置</a>
         */
        private String locationIndexCode;

        /**
         * 所属位置名称
         */
        private String locationName;

        /**
         * 扩展字段1，暂不使用
         */
        private String extend1;

        /**
         * 扩展字段2，暂不使用
         */
        private String extend2;

        /**
         * 事件开始时间，ISO8601格式：yyyy-MM-ddTHH:mm:ss.zzz+当前时区，例如北京时间：2018-07-26T15:00:00.000+08:00
         */
        private String startTime;

        /**
         * 事件扩展信息
         */
        private String data;
    }

}
