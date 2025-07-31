package com.calendar.client.oauth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao")
data class KakaoProperties(
    val clientId: String,
    val redirectUri: String,
)
