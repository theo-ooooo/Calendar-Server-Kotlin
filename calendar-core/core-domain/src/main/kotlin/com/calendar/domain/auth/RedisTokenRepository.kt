package com.calendar.domain.auth

interface RedisTokenRepository {
    fun create(
        accessToken: String,
        refreshToken: String,
        providerDetail: ProviderDetail,
        accessTokenExpiration: Long,
        refreshTokenExpiration: Long,
    ): TokenWithAuthentication

    fun findByToken(token: String): TokenWithAuthentication

    fun deleteToken(token: String)

    fun deleteAllToken(token: String)
}