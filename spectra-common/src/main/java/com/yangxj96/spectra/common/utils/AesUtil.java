package com.yangxj96.spectra.common.utils;

import com.yangxj96.spectra.common.exception.AESException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

/**
 * AES加解密
 */
@Slf4j
public class AesUtil {

    private AesUtil() {
    }

    /**
     * 算法规则<br>
     * </br>
     * 最安全的算法应该是AES/GCM/NoPadding,但是前端无法解密
     * 备用 AES/CBC/PKCS7Padding
     */
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    static {
        // 使其支持PKCS7Padding
        Security.addProvider(new BouncyCastleProvider());
    }

    private static byte[] getRandomIv() {
        var nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    private static SecretKey getRandomKey() throws NoSuchAlgorithmException {
        var kg = KeyGenerator.getInstance("AES");
        kg.init(256, SecureRandom.getInstanceStrong());
        return kg.generateKey();
    }

    /**
     * 加密
     *
     * @param origin 源
     * @return 加密结果的hex值
     */
    public static String encrypt(String origin) {
        if (StringUtils.isBlank(origin)) {
            log.info("加密字符串为空");
            return null;
        }
        try {
            var iv = getRandomIv();
            var secret = getRandomKey();
            var cipher = Cipher.getInstance(ALGORITHM);
            // GCM加密方式
            // cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(128, iv))
            cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(iv));
            var ciphertext = cipher.doFinal(origin.getBytes(StandardCharsets.UTF_8));
            // 把key iv ciphertext按照顺序压入字节数组
            var bytes = ByteBuffer.allocate(secret.getEncoded().length + iv.length + ciphertext.length)
                    .put(secret.getEncoded())
                    .put(iv)
                    .put(ciphertext)
                    .array();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new AESException(e);
        }
    }

    /**
     * 解码
     *
     * @param ciphertext 密文
     * @return 解码后的结果
     */
    public static String decrypt(String ciphertext) {
        try {
            // BASE64解码
            var decode = Base64.getDecoder().decode(ciphertext);
            var wrap = ByteBuffer.wrap(decode);
            wrap.flip();
            var slice = wrap.slice();
            // 获取key
            var key = new byte[32];
            slice.get(key);
            // 获取向量
            var iv = new byte[16];
            slice.get(iv);
            // 获取具体的内容
            var plain = new byte[slice.remaining()];
            slice.get(plain);
            // 进行解码
            var cipher = Cipher.getInstance(ALGORITHM);
            // GCM解密方式
            // cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new
            // GCMParameterSpec(128, iv))
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            var bytes = cipher.doFinal(plain);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new AESException(e);
        }
    }

}
