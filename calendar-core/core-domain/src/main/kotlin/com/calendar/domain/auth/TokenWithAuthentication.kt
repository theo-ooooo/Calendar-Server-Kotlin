package com.calendar.domain.auth

data class TokenWithAuthentication(
    val accessToken: String,
    val refreshToken: String,
    val provider: ProviderDetail,
)
