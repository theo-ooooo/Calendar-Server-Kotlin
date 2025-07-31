package com.calendar.domain.global.exception

data class ErrorException(
    val errorType: ErrorType,
    val data: Any? = null,
): RuntimeException(errorType.message)
