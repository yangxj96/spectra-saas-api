package io.github.yangxj96.starter.security.store.impl

import io.github.yangxj96.bean.security.Token
import io.github.yangxj96.bean.security.TokenAccess
import io.github.yangxj96.bean.security.TokenRefresh
import io.github.yangxj96.bean.user.User
import io.github.yangxj96.common.utils.ConvertUtil
import io.github.yangxj96.starter.security.store.TokenStore
import io.github.yangxj96.starter.security.store.redis.JdkSerializationStrategy
import io.github.yangxj96.starter.security.store.redis.RedisTokenStoreSerializationStrategy
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority


/**
 * redis 存储token的实现
 */
class RedisTokenStore(private val connectionFactory: RedisConnectionFactory) : TokenStore {

    companion object {
        private const val ACCESS_PREFIX = "access:"
        private const val ACCESS_TO_USER_PREFIX = "access_to_user:"
        private const val REFRESH_PREFIX = "refresh:"
        private const val ACCESS_TO_REFRESH_PREFIX = "access_to_refresh:"
        private const val REFRESH_TO_ACCESS_PREFIX = "refresh_to_access:"
        private const val AUTHORITY_PREFIX = "authority_token:"
    }

    private val valueSerializer: RedisTokenStoreSerializationStrategy = JdkSerializationStrategy()
    private val keySerializer = StringRedisSerializer()
    override fun create(auth: Authentication): Token {
        // token存在则返回已存在的token信息,否则创建token
        var token = exists(auth)
        token = if (token != null) {
            return token
        } else {
            Token.generate(auth)
        }

        val accessKey = ACCESS_PREFIX + token.accessToken
        val refreshKey = REFRESH_PREFIX + token.refreshToken
        val accessToUserKey = ACCESS_TO_USER_PREFIX + token.username
        val accessToRefreshKey = ACCESS_TO_REFRESH_PREFIX + token.accessToken
        val refreshToAccessKey = REFRESH_TO_ACCESS_PREFIX + token.refreshToken
        val authorityKey = AUTHORITY_PREFIX + token.accessToken

        val access = TokenAccess()
        access.token = token.accessToken
        access.username = token.username
        access.authentication = ConvertUtil.objectToByte(auth)
        access.expirationTime = token.expirationTime

        val refresh = TokenRefresh()
        refresh.accessId = access.id
        refresh.token = token.refreshToken
        refresh.expirationTime = token.expirationTime!!.plusHours(1)

        connectionFactory.connection.use { conn ->
            conn.openPipeline()
            val commands = conn.stringCommands()
            commands[wrapKey(accessKey)] = wrapValue(access)
            commands[wrapKey(refreshKey)] = wrapValue(refresh)
            commands[wrapKey(accessToUserKey)] = wrapValue(token.accessToken)
            commands[wrapKey(accessToRefreshKey)] = wrapValue(token.refreshToken)
            commands[wrapKey(refreshToAccessKey)] = wrapValue(token.accessToken)
            commands[wrapKey(authorityKey)] = wrapValue(auth)
            val keyCommands = conn.keyCommands()
            keyCommands.expire(wrapKey(accessKey), 3600)
            keyCommands.expire(wrapKey(refreshKey), 7200)
            keyCommands.expire(wrapKey(accessToUserKey), 3600)
            keyCommands.expire(wrapKey(accessToRefreshKey), 3600)
            keyCommands.expire(wrapKey(refreshToAccessKey), 3600)
            keyCommands.expire(wrapKey(authorityKey), 3600)
            conn.closePipeline()
        }
        return token
    }

    override fun read(token: String): Authentication? {
        connectionFactory.connection.use { connection ->
            if (connection.keyCommands().exists(wrapKey(ACCESS_PREFIX + token)) == true) {
                val bytes = connection.stringCommands()[wrapKey(AUTHORITY_PREFIX + token)]
                return deserializeAuthentication(bytes)
            }
        }
        return null
    }

    override fun remove(token: String) {
        connectionFactory.connection.use { connection ->
            if (connection.keyCommands().exists(wrapKey(ACCESS_TO_REFRESH_PREFIX + token)) == true) {

                val commands = connection.stringCommands()
                val refreshBytes = commands[wrapKey(ACCESS_TO_REFRESH_PREFIX + token)]
                val refresh = deserialize(refreshBytes)
                val accessBytes = commands[wrapKey(ACCESS_PREFIX + token)]
                val access = deserializeTokenAccess(accessBytes)
                connection.openPipeline()
                connection.keyCommands().del(wrapKey(ACCESS_PREFIX + token))
                connection.keyCommands().del(wrapKey(REFRESH_PREFIX + refresh))
                connection.keyCommands().del(wrapKey(AUTHORITY_PREFIX + token))
                connection.keyCommands().del(wrapKey(ACCESS_TO_REFRESH_PREFIX + token))
                connection.keyCommands().del(wrapKey(ACCESS_TO_USER_PREFIX + access.username))
                connection.keyCommands().del(wrapKey(REFRESH_TO_ACCESS_PREFIX + refresh))
                connection.closePipeline()
            }
        }
    }

    override fun refresh(refreshToken: String): Token? {
        connectionFactory.connection.use { conn ->
            if (conn.keyCommands().exists(wrapKey(REFRESH_TO_ACCESS_PREFIX + refreshToken)) == true) {
                val access = deserialize(conn.stringCommands()[wrapKey(REFRESH_TO_ACCESS_PREFIX + refreshToken)])
                val auth = deserializeAuthentication(conn.stringCommands()[wrapKey(AUTHORITY_PREFIX + access)])
                remove(access)
                return create(auth)
            }
        }
        return null
    }

    override fun check(token: String): Token? {
        val key = ACCESS_PREFIX + token
        connectionFactory.connection.use { conn ->
            if (conn.keyCommands().exists(wrapKey(key)) == true) {
                val access = deserializeTokenAccess(conn.stringCommands()[wrapKey(key)])
                return wrap(access.username)
            }
        }
        return null
    }

    override fun autoClean() {
        //  redis有自己的过期策略
    }
    /////////////////////// 私有方法区 //////////////////////////////////////
    /**
     * 根据[Authentication]判断token是否存在
     *
     * @param auth [Authentication]
     * @return 存在返回token信息, 否则返回null
     */
    private fun exists(auth: Authentication): Token? {
        connectionFactory.connection.use { conn ->
            val username = (auth.principal as User).getUsername()
            if (conn.keyCommands().exists(wrapKey(ACCESS_TO_USER_PREFIX + username)) == true) {
                return wrap(username)
            }
        }
        return null
    }

    /**
     * 根据access token包装token信息
     *
     * @param username 用户名
     * @return token信息
     */
    private fun wrap(username: String?): Token {
        connectionFactory.connection.use { conn ->
            // access token
            val accessToken = deserialize(conn.stringCommands()[wrapKey(ACCESS_TO_USER_PREFIX + username)])
            val access = deserializeTokenAccess(conn.stringCommands()[wrapKey(ACCESS_PREFIX + accessToken)])
            // refresh token
            val refreshToken = deserialize(conn.stringCommands()[wrapKey(ACCESS_TO_REFRESH_PREFIX + accessToken)])
            val refresh = deserializeTokenRefresh(conn.stringCommands()[wrapKey(REFRESH_PREFIX + refreshToken)])
            // 获取权限
            val authority = ArrayList<String>()
            val authentication =
                deserializeAuthentication(conn.stringCommands()[wrapKey(AUTHORITY_PREFIX + accessToken)])
            authentication.authorities.forEach { item: GrantedAuthority? -> authority.add(item!!.authority) }
            // 构建
            val token = Token()
            token.username = access.username
            token.accessToken = access.token
            token.refreshToken = refresh.token
            token.authorities = authority
            token.expirationTime = access.expirationTime

            return token
        }
    }

    private fun wrapKey(key: String): ByteArray {
        return keySerializer.serialize(key)
    }

    private fun wrapValue(obj: Any): ByteArray {
        return valueSerializer.serialize(obj)
    }

    private fun wrapValue(`val`: String?): ByteArray {
        return valueSerializer.serialize(`val`)
    }

    private fun deserializeAuthentication(bytes: ByteArray?): Authentication {
        return valueSerializer.deserialize(bytes, Authentication::class.java)
    }

    private fun deserializeTokenAccess(bytes: ByteArray?): TokenAccess {
        return valueSerializer.deserialize(bytes, TokenAccess::class.java)
    }

    private fun deserializeTokenRefresh(bytes: ByteArray?): TokenRefresh {
        return valueSerializer.deserialize(bytes, TokenRefresh::class.java)
    }

    private fun deserialize(bytes: ByteArray?): String {
        return valueSerializer.deserializeString(bytes)
    }


}
