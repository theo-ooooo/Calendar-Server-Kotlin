package com.calendar.presentation.v1.auth

import com.calendar.client.oauth.OAuthService
import com.calendar.client.oauth.config.KakaoProperties
import com.calendar.presentation.v1.annotation.ApiV1Controller
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.util.UriComponentsBuilder

@Tag(name ="Auth API", description = "인증 관련 API")
@ApiV1Controller
class AuthController(
    private val oAuthService: OAuthService,
    private val kakaoProperties: KakaoProperties
) {

    @Operation(summary = "카카오 로그인 페이지", description = "카카오 로그인 페이지로 이동합니다.")
    @GetMapping("/auth/kakao/redirect")
    fun kakaoRedirect(): RedirectView {
        val uri = UriComponentsBuilder
            .newInstance()
            .scheme("https")
            .host("kauth.kakao.com")
            .path("/oauth/authorize")
            .queryParam("client_id", kakaoProperties.clientId)
            .queryParam("redirect_uri", kakaoProperties.redirectUri)
            .queryParam("response_type", "code")
            .build()
            .toUri()

        return RedirectView(uri.toString())
    }
}