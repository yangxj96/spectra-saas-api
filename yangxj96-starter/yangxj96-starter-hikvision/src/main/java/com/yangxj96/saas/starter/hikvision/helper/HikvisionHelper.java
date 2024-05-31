package com.yangxj96.saas.starter.hikvision.helper;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.hikvision.artemis.sdk.constant.ContentType;
import com.yangxj96.saas.starter.hikvision.exception.ArtemisException;
import com.yangxj96.saas.starter.hikvision.props.HikvisionProperties;
import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.response.HikvisionResult;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 海康相关帮助类
 */
@Slf4j
public class HikvisionHelper {

    private static final String HIKVISION_OBJECT_MAPPER_NAME = "hikvisionObjectMapper";

    /**
     * 构建路径
     *
     * @param path 请求路径
     * @return 构建好的路径
     */
    public static Map<String, String> assemblePath(String path) {
        var props = SpringUtil.getBean(HikvisionProperties.class);
        if (props == null) {
            throw new ArtemisException("获取海康相关配置bean失败");
        }
        var map = new HashMap<String, String>();
        map.put(props.getProtocol(), props.getPrefix() + path);
        return map;
    }

    /**
     * post方法请求
     *
     * @param url   请求地址
     * @param body  参数实体
     * @param clazz 响应类型
     * @param <P>   参数实体类型
     * @param <T>   响应类型
     * @return 只会返回成功序列化的响应
     * @throws Exception e
     */
    public static <P, T> T postRequest(String url, P body, Class<T> clazz) throws Exception {
        var om = SpringUtil.getBean(HIKVISION_OBJECT_MAPPER_NAME, ObjectMapper.class);
        return postRequest(url, om.writeValueAsString(body), clazz);
    }

    /**
     * post方法请求
     *
     * @param url   请求地址
     * @param body  参数JSON字符串
     * @param clazz 响应类型
     * @param <T>   响应类型
     * @return 只会返回成功序列化的响应
     * @throws Exception e
     */
    public static <T> T postRequest(String url, String body, Class<T> clazz) throws Exception {
        var result = ArtemisHttpUtil.doPostStringArtemis(
                getConfig(),
                assemblePath(url),
                body,
                null,
                null,
                ContentType.CONTENT_TYPE_JSON
        );
        var om = getObjectMapper();
        var resp = om.readValue(result, HikvisionResult.class);
        if (!"0".equals(resp.getCode())) {
            throw new ArtemisException("海康接口请求异常,code:" + resp.getCode() + ",msg:" + resp.getMsg());
        }
        if (resp.getData() == null) {
            throw new ArtemisException("响应data为null");
        }
        return om.readValue(om.writeValueAsString(resp.getData()), clazz);
    }

    /**
     * post方法请求
     *
     * @param url  请求地址
     * @param body 参数实体
     * @param <P>  参数实体
     * @throws Exception e
     */
    public static <P> void postRequest(String url, P body) throws Exception {
        var om = SpringUtil.getBean(HIKVISION_OBJECT_MAPPER_NAME, ObjectMapper.class);
        postRequest(url, om.writeValueAsString(body));
    }

    /**
     * post方法请求
     *
     * @param url  请求地址
     * @param body 参数JSON字符串
     * @throws Exception e
     */
    public static void postRequest(String url, String body) throws Exception {
        var result = ArtemisHttpUtil.doPostStringArtemis(
                getConfig(),
                assemblePath(url),
                body,
                null,
                null,
                ContentType.CONTENT_TYPE_JSON
        );
        var om = getObjectMapper();
        var resp = om.readValue(result, HikvisionResult.class);
        if (!"0".equals(resp.getCode())) {
            throw new ArtemisException("海康接口请求异常,code:" + resp.getCode() + ",msg:" + resp.getMsg());
        }
    }

    /**
     * post请求结果为分页的
     *
     * @param url   请求地址
     * @param body  map格式的请求参数
     * @param clazz 分页里数据列表的实体
     * @param <T>   分页里数据列表的实体类型
     * @return 分页信息
     * @throws Exception e
     */
    public static <T> HikvisionPage<T> postRequestPage(String url, Map<String, Object> body, TypeReference<HikvisionPage<T>> clazz) throws Exception {
        var om = SpringUtil.getBean(HIKVISION_OBJECT_MAPPER_NAME, ObjectMapper.class);
        return postRequestPage(url, om.writeValueAsString(body), clazz);
    }

    /**
     * post请求结果为分页的
     *
     * @param url   请求地址
     * @param body  参数JSON字符串
     * @param clazz 分页里数据列表的实体
     * @param <T>   分页里数据列表的实体类型
     * @return 分页信息
     * @throws Exception e
     */
    public static <T> HikvisionPage<T> postRequestPage(String url, String body, TypeReference<HikvisionPage<T>> clazz) throws Exception {
        var result = ArtemisHttpUtil.doPostStringArtemis(
                getConfig(),
                assemblePath(url),
                body,
                null,
                null,
                ContentType.CONTENT_TYPE_JSON
        );
        var om = getObjectMapper();
        var resp = om.readValue(result, HikvisionResult.class);
        if (!"0".equals(resp.getCode())) {
            throw new ArtemisException("海康接口请求异常,code:" + resp.getCode() + ",msg:" + resp.getMsg());
        }
        if (resp.getData() == null) {
            throw new ArtemisException("响应data为null");
        }
        return om.readValue(om.writeValueAsString(resp.getData()), clazz);
    }

    /**
     * 获取配置信息bean
     *
     * @return {@link ArtemisConfig}
     */
    private static ArtemisConfig getConfig() {
        return SpringUtil.getBean(ArtemisConfig.class);
    }

    /**
     * 获取Jackson
     *
     * @return {@link ObjectMapper}
     */
    private static ObjectMapper getObjectMapper() {
        return SpringUtil.getBean(HIKVISION_OBJECT_MAPPER_NAME, ObjectMapper.class);
    }

}
