/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-03-28 22:27:27
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.common.utils;

import cn.hutool.core.util.HexUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * AES加解密
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/3/28 22:27
 */
//@Slf4j
public class AesUtil {

    /**
     * 加密
     *
     * @param origin 源
     * @param secret 密钥
     * @param iv     偏移
     * @return 加密结果的hex值
     */
    public static byte[] encrypt(String origin, SecretKey secret, byte[] iv) {
        if (StringUtils.isBlank(origin)) {
            //log.info("加密字符串为空");
            return new byte[0];
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(128, iv));
            byte[] ciphertext = cipher.doFinal(origin.getBytes(StandardCharsets.UTF_8));
            // 把
            return ByteBuffer.allocate(secret.getEncoded().length + iv.length  + ciphertext.length)
                    .put(secret.getEncoded())
                    .put(iv)
                    .put(ciphertext)
                    .array();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // , SecretKey secret

    public static String decrypt(byte[] ciphertext) {
        try {
            ByteBuffer wrap = ByteBuffer.wrap(ciphertext);

            byte[] key = new byte[32];
            wrap.get(key);

            byte[] iv = new byte[16];
            wrap.get(iv);

            byte[] plain = new byte[wrap.remaining()];
            wrap.get(plain);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
            byte[] bytes = cipher.doFinal(plain);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取随机偏移量
     *
     * @return 偏移量
     */
    public static byte @NotNull [] getRandomIv() {
        byte[] nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * 获取随机KEY
     *
     * @return key
     * @throws NoSuchAlgorithmException 没找到加密算法
     */
    public static SecretKey getRandomKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256, SecureRandom.getInstanceStrong());
        return kg.generateKey();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String OUTPUT_FORMAT = "%-30s:%s";

        byte[] nonce = getRandomIv();
        SecretKey secret = getRandomKey();

        String origin = "Hello World! AES GCM!";

        byte[] encrypt = encrypt(origin, secret, nonce);

        System.out.println("\n------ AES GCM Encryption ------");
        System.out.println(String.format(OUTPUT_FORMAT, "Input (plain text)", origin));
        System.out.println(String.format(OUTPUT_FORMAT, "Key (hex)", HexUtil.encodeHexStr(secret.getEncoded())));
        System.out.println(String.format(OUTPUT_FORMAT, "IV  (hex)", HexUtil.encodeHexStr(nonce)));
        System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (hex) ", HexUtil.encodeHexStr(encrypt)));

        System.out.println("\n------ AES GCM Decryption ------");
        System.out.println(String.format(OUTPUT_FORMAT, "Input (hex)", HexUtil.encodeHexStr(encrypt)));
        System.out.println(String.format(OUTPUT_FORMAT, "Key (hex)", HexUtil.encodeHexStr(secret.getEncoded())));

        String decryptedText = decrypt(encrypt);
        System.out.println(String.format(OUTPUT_FORMAT, "Decrypted (plain text)", decryptedText));
    }

}
