
package io.github.yangxj96.common.utils;

import java.io.*;
import java.util.*;

/**
 * 转换工具类
 */
public class ConvertUtil {

    private ConvertUtil(){}


    /**
     * obj 转 list map,固定map的key为string 类型
     *
     * @param obj    待转换的对象
     * @param vClass map的value的类型
     * @param <V>    V 的类型
     * @return 转换后的list map,可能为空
     */
    public static <V> List<Map<String, V>> objToListMap(Object obj, Class<V> vClass) {
        return objToListMap(obj, String.class, vClass);
    }


    /**
     * obj 转 list map
     *
     * @param obj    待转换的对象
     * @param kCalzz map的key的类型
     * @param vCalzz map的value的类型
     * @param <K>    K 的类型
     * @param <V>    V 的类型
     * @return 转换后的list map,可能为空
     */
    public static <K, V> List<Map<K, V>> objToListMap(Object obj, Class<K> kCalzz, Class<V> vCalzz) {
        if (!(obj instanceof List<?>)) {
            return Collections.emptyList();
        }
        List<Map<K, V>> result = new ArrayList<>();
        for (Object mapObj : (List<?>) obj) {
            if (mapObj instanceof Map<?, ?>) {
                Map<K, V> map = new HashMap<>(16);
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) mapObj).entrySet()) {
                    map.put(kCalzz.cast(entry.getKey()), vCalzz.cast(entry.getValue()));
                }
                result.add(map);
            }
        }
        return result;
    }


    /**
     * 对象转list
     *
     * @param obj   待转换的对象
     * @param clazz list item的类型
     * @param <T>   list item的类型
     * @return 转换后的list, 可能为空
     */
    public static <T> List<T> objToList(Object obj, Class<T> clazz) {
        if (!(obj instanceof List<?>)) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        for (Object o : (List<?>) obj) {
            result.add(clazz.cast(o));
        }
        return result;
    }


    /**
     * 字节数组转对象
     *
     * @param bytes 字节数组
     * @return 转换后的对象
     */
    public static Object byteToObject(byte[] bytes) {
        try (
                ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(stream)
        ) {
            return oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("字节数组转对象异常");
        }
    }


    /**
     * 对象转字节数组
     *
     * @param obj 对象
     * @return 字节数组
     */
    public static byte[] objectToByte(Object obj) {
        try (
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(bo)
        ) {
            oo.writeObject(obj);
            return bo.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("对象转字节数组异常");
        }
    }

}
