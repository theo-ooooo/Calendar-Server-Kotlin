package com.calendar.api.presentation.v1.auth.response

import com.calendar.domain.auth.token.Token
import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "토큰 응답 Json")
data class TokenResponse(
    @Schema(description = "accessToken", example = "accessToken")
    val accessToken: String,
    @Schema(description = "refreshToken", example = "refreshToken")
    val refreshToken: String,
) {
    companion object {
        fun toResponse(
            token: Token
        ): TokenResponse =
            TokenResponse(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
    }
}
