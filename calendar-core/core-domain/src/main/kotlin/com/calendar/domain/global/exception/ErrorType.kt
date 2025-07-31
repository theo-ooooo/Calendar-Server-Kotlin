package com.calendar.domain.global.exception

enum class ErrorType(
    val status: Int,
    val kind: ErrorKind,
    val message: String,
) {
    /** Common */
    DEFAULT(500, ErrorKind.INTERNAL_SERVER_ERROR, "예기치 못한 오류가 발생했습니다."),
    NOT_FOUND_DATA(404, ErrorKind.NOT_FOUND, "해당 데이터를 찾지 못했습니다."),
    INVALID_REQUEST(400, ErrorKind.CLIENT_ERROR, "잘못된 요청입니다."),
    INVALID_SIMULTANEOUS_REQUEST(400, ErrorKind.CLIENT_ERROR, "동시에 요청할 수 없습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(400, ErrorKind.CLIENT_ERROR, "요청 한 값 타입이 잘못되어 binding에 실패하였습니다."),
    METHOD_NOT_ALLOWED(400, ErrorKind.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP method 입니다."),
    INTERNAL_SERVER_ERROR(500, ErrorKind.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),

    /** Authorization */
    UNAUTHORIZED_TOKEN(401, ErrorKind.AUTHORIZATION, "인증정보가 필요한 요청입니다."),
    INVALID_TOKEN(401, ErrorKind.AUTHORIZATION, "유효하지 않은 토큰입니다."),
    INVALID_ACCESS_TOKEN(401, ErrorKind.AUTHORIZATION, "잘못된 accessToken 입니다."),
    INVALID_REFRESH_TOKEN(401, ErrorKind.AUTHORIZATION, "잘못된 refreshToken 입니다."),
    INVALID_SOCIAL_PROVIDER(400, ErrorKind.CLIENT_ERROR, "지원하지 않는 소셜 제공자입니다."),
}