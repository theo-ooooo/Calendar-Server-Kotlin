package com.calendar.storage.redis

import com.calendar.domain.auth.ProviderDetail
import com.calendar.domain.auth.RedisTokenRepository
import com.calendar.domain.auth.TokenWithAuthentication
import com.calendar.domain.global.exception.ErrorException
import com.calendar.domain.global.exception.ErrorType
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisTokenCoreRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
): RedisTokenRepository {
    override fun create(
        accessToken: String,
        refreshToken: String,
        providerDetail: ProviderDetail,
        accessTokenExpiration: Long,
        refreshTokenExpiration: Long
    ): TokenWithAuthentication {
        val tokenWithAuthentication = TokenWithAuthentication(
            accessToken = accessToken,
            refreshToken = refreshToken,
            provider = providerDetail,
        )

        redisTemplate.opsForValue().apply {
            set(accessToken,
                objectMapper.writeValueAsString(tokenWithAuthentication),
                Duration.ofSeconds(accessTokenExpiration * 60L),
            )

            set(refreshToken,
                objectMapper.writeValueAsString(tokenWithAuthentication),
                Duration.ofSeconds(refreshTokenExpiration * 60L),)
        }

        return tokenWithAuthentication
    }

    override fun findByToken(token: String): TokenWithAuthentication {
       val value = requireNotNull(redisTemplate.opsForValue().get(token)) {
           throw ErrorException(ErrorType.INVALID_TOKEN)
       }
        return objectMapper.readValue(value, TokenWithAuthentication::class.java)
    }

    override fun deleteToken(token: String) {
        redisTemplate.delete(token)
    }

    override fun deleteAllToken(token: String) {
        val tokenWithAuthentication = redisTemplate.opsForValue().get(token)?.let {
            objectMapper.readValue(it, TokenWithAuthentication::class.java)
        } ?: throw ErrorException(ErrorType.INVALID_TOKEN)

        redisTemplate.delete(tokenWithAuthentication.accessToken)
        redisTemplate.delete(tokenWithAuthentication.refreshToken)


    }

}