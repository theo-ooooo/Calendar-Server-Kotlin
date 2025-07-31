package com.calendar.api.presentation.v1.auth.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "카카오 코드 로그인")
data class KakaoCodeRequest(
    @Schema(description = "카카오 코드", example = "1234")
    val code: String,
)
