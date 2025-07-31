package com.calendar.api.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt-token")
data class AuthenticationProperties(
    val accessTokenExpirationSeconds: Long,
    val refreshTokenExpirationSeconds: Long,
)
