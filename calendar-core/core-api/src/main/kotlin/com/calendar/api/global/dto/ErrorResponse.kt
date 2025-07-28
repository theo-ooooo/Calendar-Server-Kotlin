package com.calendar.api.global.dto

data class ErrorResponse(
    val errorCode: String,
    val message: String
) {
    companion object {
        fun of(
            errorCode: String,
            message: String
        ) = ErrorResponse(errorCode, message)
    }
}
