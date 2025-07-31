package com.calendar.api.presentation.v1.auth.response

import io.swagger.v3.oas.annotations.media.Schema

data class LogoutResponse(
    @Schema(description = "로그아웃 메시지", example = "userKey: 로그아웃 되었습니다.")
    val message: String,
)
