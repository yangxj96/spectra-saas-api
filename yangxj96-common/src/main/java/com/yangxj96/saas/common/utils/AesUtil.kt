package com.yangxj96.saas.common.utils

import org.apache.commons.lang3.StringUtils
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.Security
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES加解密
 */
object AesUtil {

    /**
     * 算法规则<br></br>
     * 最安全的算法应该是AES/GCM/NoPadding,但是前端无法解密
     * 备用 AES/CBC/PKCS7Padding
     */
    private const val ALGORITHM = "AES/GCM/NoPadding"

    private val log = LoggerFactory.getLogger(this::class.java)

    init {
        // 使其支持PKCS7Padding
        Security.addProvider(BouncyCastleProvider())
    }

    private val randomIv: ByteArray
        /**
         * 获取随机偏移量
         *
         * @return 偏移量
         */
        get() {
            val nonce = ByteArray(16)
            SecureRandom().nextBytes(nonce)
            return nonce
        }

    @get:Throws(NoSuchAlgorithmException::class)
    private val randomKey: SecretKey
        /**
         * 获取随机KEY
         *
         * @return key
         * @throws NoSuchAlgorithmException 没找到加密算法
         */
        get() {
            val kg = KeyGenerator.getInstance("AES")
            kg.init(256, SecureRandom.getInstanceStrong())
            return kg.generateKey()
        }

    /**
     * 加密
     *
     * @param origin 源
     * @return 加密结果的hex值
     */
    @JvmStatic
    fun encrypt(origin: String): String? {
        if (StringUtils.isBlank(origin)) {
            log.info("加密字符串为空")
            return null
        }
        return try {
            val iv = randomIv
            val secret = randomKey
            val cipher = Cipher.getInstance(ALGORITHM)
            // GCM加密方式
            //cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(128, iv))
            cipher.init(Cipher.ENCRYPT_MODE, secret, IvParameterSpec(iv))
            val ciphertext = cipher.doFinal(origin.toByteArray(StandardCharsets.UTF_8))
            // 把key iv ciphertext按照顺序压入字节数组
            val bytes = ByteBuffer.allocate(secret.encoded.size + iv.size + ciphertext.size)
                .put(secret.encoded)
                .put(iv)
                .put(ciphertext)
                .array()
            Base64.getEncoder().encodeToString(bytes)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * 解码
     *
     * @param ciphertext 密文
     * @return 解码后的结果
     */
    @JvmStatic
    fun decrypt(ciphertext: String?): String {
        return try {
            // BASE64解码
            val decode = Base64.getDecoder().decode(ciphertext)
            val wrap = ByteBuffer.wrap(decode)
            // 获取key
            val key = ByteArray(32)
            wrap[key]
            // 获取向量
            val iv = ByteArray(16)
            wrap[iv]
            // 获取具体的内容
            val plain = ByteArray(wrap.remaining())
            wrap[plain]
            // 进行解码
            val cipher = Cipher.getInstance(ALGORITHM)
            // GCM解密方式
            //cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv))
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(iv))
            val bytes = cipher.doFinal(plain)
            String(bytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }


}
