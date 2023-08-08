package io.github.yangxj96.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;

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

    /**
     * 算法规则<br/>
     * 最安全的算法应该是AES/GCM/NoPadding,但是前端无法解密
     */
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";

    private AesUtil() {
    }

    static  {
        // 使其支持PKCS7Padding
        Security.addProvider(new BouncyCastleProvider());
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
            // @formatter:off
            byte[] iv        = getRandomIv();
            SecretKey secret = getRandomKey();
            Cipher cipher    = Cipher.getInstance(ALGORITHM);
            // @formatter:on
            // GCM加密方式
            //cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(128, iv))
            cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(iv));
            byte[] ciphertext = cipher.doFinal(origin.getBytes(StandardCharsets.UTF_8));
            // 把key iv ciphertext按照顺序压入字节数组
            byte[] bytes = ByteBuffer.allocate(secret.getEncoded().length + iv.length + ciphertext.length)
                    .put(secret.getEncoded())
                    .put(iv)
                    .put(ciphertext)
                    .array();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            byte[] decode = Base64.getDecoder().decode(ciphertext);
            ByteBuffer wrap = ByteBuffer.wrap(decode);
            // 获取key
            byte[] key = new byte[32];
            wrap.get(key);
            // 获取向量
            byte[] iv = new byte[16];
            wrap.get(iv);
            // 获取具体的内容
            byte[] plain = new byte[wrap.remaining()];
            wrap.get(plain);
            // 进行解码
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // GCM解密方式
            //cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv))
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
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
    private static byte @NotNull [] getRandomIv() {
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
    private static SecretKey getRandomKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256, SecureRandom.getInstanceStrong());
        return kg.generateKey();
    }

}
