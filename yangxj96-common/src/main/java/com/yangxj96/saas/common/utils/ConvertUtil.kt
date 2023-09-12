/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.common.utils

import java.io.*

/**
 * 转换工具类
 */
object ConvertUtil {

    /**
     * obj 转 list map,固定map的key为string 类型
     *
     * @param obj    待转换的对象
     * @param vClass map的value的类型
     * @param <V>    V 的类型
     * @return 转换后的list map,可能为空
    </V> */
    fun <V> objToListMap(obj: Any?, vClass: Class<V>): List<Map<String, V>> {
        return objToListMap(obj, String::class.java, vClass)
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
    </V></K> */
    fun <K, V> objToListMap(obj: Any?, kCalzz: Class<K>, vCalzz: Class<V>): List<Map<K, V>> {
        if (obj !is List<*>) {
            return emptyList()
        }
        val result: MutableList<Map<K, V>> = ArrayList()
        for (mapObj in obj) {
            if (mapObj is Map<*, *>) {
                val map: MutableMap<K, V> = HashMap(16)
                for ((key, value) in mapObj) {
                    map[kCalzz.cast(key)] = vCalzz.cast(value)
                }
                result.add(map)
            }
        }
        return result
    }

    /**
     * 对象转list
     *
     * @param obj   待转换的对象
     * @param clazz list item的类型
     * @param <T>   list item的类型
     * @return 转换后的list, 可能为空
    </T> */
    fun <T> objToList(obj: Any?, clazz: Class<T>): List<T> {
        if (obj !is List<*>) {
            return emptyList()
        }
        val result: MutableList<T> = ArrayList()
        for (o in obj) {
            result.add(clazz.cast(o))
        }
        return result
    }

    /**
     * 字节数组转对象
     *
     * @param bytes 字节数组
     * @return 转换后的对象
     */
    fun byteToObject(bytes: ByteArray?): Any {
        try {
            ByteArrayInputStream(bytes).use { stream -> ObjectInputStream(stream).use { oi -> return oi.readObject() } }
        } catch (e: IOException) {
            throw RuntimeException("字节数组转对象异常")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("字节数组转对象异常")
        }
    }

    /**
     * 对象转字节数组
     *
     * @param obj 对象
     * @return 字节数组
     */
    fun objectToByte(obj: Any?): ByteArray {
        try {
            ByteArrayOutputStream().use { bo ->
                ObjectOutputStream(bo).use { oo ->
                    oo.writeObject(obj)
                    return bo.toByteArray()
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("对象转字节数组异常")
        }
    }
}
