package com.calendar.api.global.config

import com.calendar.api.global.dto.ApiResponse
import com.calendar.api.global.dto.ErrorResponse
import com.calendar.domain.global.exception.ErrorType
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jdk.internal.joptsimple.internal.Messages.message
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        with(response) {
            status = HttpStatus.UNAUTHORIZED.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = "UTF-8"
            writer.write(objectMapper.writeValueAsString(
                    ApiResponse.fail(
                        status = HttpStatus.UNAUTHORIZED.value(),
                        response = ErrorResponse(
                            errorCode = ErrorType.UNAUTHORIZED_TOKEN.name,
                            message = ErrorType.UNAUTHORIZED_TOKEN.message,
                        )
                    )
            ))
        }
    }
}