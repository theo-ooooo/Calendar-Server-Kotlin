package com.calendar.domain.auth.token

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
