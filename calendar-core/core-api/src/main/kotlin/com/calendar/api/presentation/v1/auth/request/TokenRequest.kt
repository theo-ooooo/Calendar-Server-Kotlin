package com.calendar.api.presentation.v1.auth.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 요청 Json")
data class TokenRequest(
    @Schema(description = "Token", example = "accessToken")
    val token: String,
)
