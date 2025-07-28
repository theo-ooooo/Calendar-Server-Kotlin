package com.calendar.domain.global.exception

enum class ErrorType(
    val status: Int,
    val kind: ErrorKind,
    val message: String,
) {
    /** Authorization */
    UNAUTHORIZED_TOKEN(401, ErrorKind.AUTHORIZATION, "인증정보가 필요한 요청입니다."),
}