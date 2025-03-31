package com.yangxj96.spectra.starter.hikvision.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.hikvision.artemis.sdk.constant.ContentType;
import com.yangxj96.spectra.starter.hikvision.exception.ArtemisException;
import com.yangxj96.spectra.starter.hikvision.props.HikvisionProperties;
import com.yangxj96.spectra.starter.hikvision.response.HikvisionPage;
import com.yangxj96.spectra.starter.hikvision.response.HikvisionResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 海康相关帮助类
 */
@Slf4j
@Component
public class HikvisionTemplate {

    private static final String HIKVISION_OBJECT_MAPPER_NAME = "hikvisionObjectMapper";

    @Resource(name = HIKVISION_OBJECT_MAPPER_NAME)
    private ObjectMapper om;

    @Resource
    private ArtemisConfig config;

    @Resource
    private HikvisionProperties properties;

    /**
     * 拼接请求地址
     *
     * @param path 请求地址
     * @return 拼接后的请求地址
     */
    public Map<String, String> assemblePath(String path) {
        var map = new HashMap<String, String>();
        map.put(properties.getProtocol(), properties.getPrefix() + path);
        return map;
    }

    /**
     * post请求
     *
     * @param url   请求地址
     * @param body  请求body
     * @param clazz 响应数据类型
     * @param <P>   参数实体类型
     * @param <T>   响应数据类型
     * @return 响应数据类型
     * @throws Exception e
     */
    public <P, T> T post(String url, P body, Class<T> clazz) throws Exception {
        return post(url, om.writeValueAsString(body), clazz);
    }

    /**
     * post请求
     *
     * @param url   请求地址
     * @param body  请求body
     * @param clazz 响应数据类型
     * @param <T>   响应数据类型
     * @return 成功响应后序列化的对象
     * @throws Exception e
     */
    public <T> T post(String url, String body, Class<T> clazz) throws Exception {
        return execute(url, body, s -> {
            try {
                var resp = om.readValue(s, HikvisionResult.class);
                if (!"0".equals(resp.getCode())) {
                    throw new ArtemisException("响应失败,code:" + resp.getCode() + ",msg:" + resp.getMsg());
                }
                if (resp.getData() == null) {
                    throw new ArtemisException("响应data为null");
                }
                return om.readValue(om.writeValueAsString(resp.getData()), clazz);
            } catch (Exception e) {
                throw new ArtemisException("未知错误," + e.getMessage());
            }
        });
    }

    /**
     * post请求,无返回内容
     *
     * @param url  请求地址
     * @param body 请求body
     * @param <P>  参数实体类型
     * @throws Exception e
     */
    public <P> void post(String url, P body) throws Exception {
        post(url, om.writeValueAsString(body));
    }

    /**
     * post请求,无返回内容
     *
     * @param url  请求地址
     * @param body 请求body
     * @throws Exception e
     */
    public void post(String url, String body) throws Exception {
        execute(url, body, s -> {
            try {
                var resp = om.readValue(s, HikvisionResult.class);
                if (!"0".equals(resp.getCode())) {
                    throw new ArtemisException("响应失败,code:" + resp.getCode() + ",msg:" + resp.getMsg());
                }
            } catch (Exception e) {
                throw new ArtemisException("未知错误," + e.getMessage());
            }
        });
    }

    /**
     * post请求,结果为分页数据
     *
     * @param url   请求地址
     * @param body  请求body
     * @param clazz 数据类型
     * @param <P>   请求参数类型实体
     * @param <T>   响应分页的LIST的类型
     * @return 分页信息
     * @throws Exception e
     */
    public <P, T> HikvisionPage<T> postPage(String url, P body, TypeReference<HikvisionPage<T>> clazz) throws Exception {
        return postPage(url, om.writeValueAsString(body), clazz);
    }

    /**
     * post请求,结果为分页数据
     *
     * @param url   请求地址
     * @param body  请求body
     * @param clazz 数据类型
     * @param <T>   响应分页的LIST的类型
     * @return 分页信息
     * @throws Exception e
     */
    public <T> HikvisionPage<T> postPage(String url, Map<String, Object> body, TypeReference<HikvisionPage<T>> clazz) throws Exception {
        return postPage(url, om.writeValueAsString(body), clazz);
    }

    /**
     * post请求,结果为分页数据
     *
     * @param url   请求地址
     * @param body  请求body
     * @param clazz 数据类型
     * @param <T>   响应分页的LIST的类型
     * @return 分页信息
     * @throws Exception e
     */
    public <T> HikvisionPage<T> postPage(String url, String body, TypeReference<HikvisionPage<T>> clazz) throws Exception {
        return execute(url, body, s -> {
            try {
                var resp = om.readValue(s, HikvisionResult.class);
                if (!"0".equals(resp.getCode())) {
                    throw new ArtemisException("海康接口请求异常,code:" + resp.getCode() + ",msg:" + resp.getMsg());
                }
                if (resp.getData() == null) {
                    throw new ArtemisException("响应data为null");
                }
                return om.readValue(om.writeValueAsString(resp.getData()), clazz);
            } catch (Exception e) {
                throw new ArtemisException("未知错误," + e.getMessage());
            }
        });
    }

    /**
     * 执行器,具体执行请求业务
     *
     * @param url      请求地址
     * @param body     请求体
     * @param function 响应处理方法
     * @param <T>      响应内容实体
     * @return 响应结果
     * @throws Exception e
     */
    public <T> T execute(String url, String body, Function<String, T> function) throws Exception {
        var str = ArtemisHttpUtil.doPostStringArtemis(
                config,
                assemblePath(url),
                body,
                null,
                null,
                ContentType.CONTENT_TYPE_JSON
        );
        return function.apply(str);
    }

    /**
     * 执行器,具体执行请求业务
     *
     * @param url      请求地址
     * @param body     请求体
     * @param consumer 响应处理方法
     * @throws Exception e
     */
    public void execute(String url, String body, Consumer<String> consumer) throws Exception {
        var str = ArtemisHttpUtil.doPostStringArtemis(
                config,
                assemblePath(url),
                body,
                null,
                null,
                ContentType.CONTENT_TYPE_JSON
        );
        consumer.accept(str);
    }

}
