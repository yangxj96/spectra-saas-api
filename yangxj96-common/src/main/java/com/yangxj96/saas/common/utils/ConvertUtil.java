package com.yangxj96.saas.common.utils;

import com.yangxj96.saas.common.exception.TransitionException;

import java.io.*;
import java.util.*;

/**
 * 转换工具类
 */
public class ConvertUtil {

    private ConvertUtil() {
    }

    /**
     * obj 转 list map,固定map的key为string 类型
     *
     * @param obj    待转换的对象
     * @param vClass map的value的类型
     * @param <V>    V 的类型
     * @return 转换后的list map,可能为空
     * </V>
     */
    public static <V> List<Map<String, V>> objToListMap(Object obj, Class<V> vClass) {
        return objToListMap(obj, String.class, vClass);
    }

    public static <K, V> List<Map<K, V>> objToListMap(Object obj, Class<K> kClass, Class<V> vClass) {
        List<Map<K, V>> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object mapObj : (List<?>) obj) {
                if (mapObj instanceof Map<?, ?>) {
                    Map<K, V> map = new HashMap<>(16);
                    for (Map.Entry<?, ?> entry : ((Map<?, ?>) mapObj).entrySet()) {
                        map.put(kClass.cast(entry.getKey()), vClass.cast(entry.getValue()));
                    }
                    result.add(map);
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    /**
     * 对象转list
     *
     * @param obj   待转换的对象
     * @param clazz list item的类型
     * @param <T>   list item的类型
     * @return 转换后的list, 可能为空
     * </T>
     */
    public static <T> List<T> objToList(Object obj, Class<T> clazz) {
        if (obj instanceof List<?>) {
            var result = new ArrayList<T>();
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return Collections.emptyList();
    }

    /**
     * 字节数组转对象
     *
     * @param bytes 字节数组
     * @return 转换后的对象
     */
    public static Object byteToObject(byte[] bytes) {
        try {
            var stream = new ByteArrayInputStream(bytes);
            var oi = new ObjectInputStream(stream);
            var object = oi.readObject();
            oi.close();
            stream.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            throw new TransitionException("字节数组转对象异常");
        }
    }

    /**
     * 对象转字节数组
     *
     * @param obj 对象
     * @return 字节数组
     */
    public static byte[] objectToByte(Object obj) {
        try {
            var bo = new ByteArrayOutputStream();
            var oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            var result = bo.toByteArray();
            bo.close();
            oo.close();
            return result;
        } catch (IOException e) {
            throw new TransitionException("对象转字节数组异常");
        }
    }
}
