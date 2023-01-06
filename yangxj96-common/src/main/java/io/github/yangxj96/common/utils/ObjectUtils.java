/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:21
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.common.utils;

import java.io.*;

/**
 * 对象工具类
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public class ObjectUtils {

    private ObjectUtils() {
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
