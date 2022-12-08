package io.github.yangxj96.common.utils;

import java.io.*;

/**
 * 对象工具类
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
