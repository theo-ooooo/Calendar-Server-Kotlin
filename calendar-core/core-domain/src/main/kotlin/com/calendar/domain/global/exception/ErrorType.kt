package com.calendar.domain.global.exception

enum class ErrorType(
    val status: Int,
    val kind: ErrorKind,
    val message: String,
) {
    /** Authorization */
    UNAUTHORIZED_TOKEN(401, ErrorKind.AUTHORIZATION, "인증정보가 필요한 요청입니다."),
    INVALID_TOKEN(401, ErrorKind.AUTHORIZATION, "유효하지 않은 토큰입니다."),
    INVALID_ACCESS_TOKEN(401, ErrorKind.AUTHORIZATION, "잘못된 accessToken 입니다."),
    INVALID_REFRESH_TOKEN(401, ErrorKind.AUTHORIZATION, "잘못된 refreshToken 입니다."),
    INVALID_SOCIAL_PROVIDER(400, ErrorKind.CLIENT_ERROR, "지원하지 않는 소셜 제공자입니다."),
}