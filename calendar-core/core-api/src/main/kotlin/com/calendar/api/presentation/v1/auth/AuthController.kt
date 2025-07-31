package com.calendar.api.presentation.v1.auth

import com.calendar.client.oauth.OAuthService
import com.calendar.client.oauth.config.KakaoProperties
import com.calendar.api.presentation.v1.annotation.ApiV1Controller
import com.calendar.api.presentation.v1.auth.request.KakaoCodeRequest
import com.calendar.api.presentation.v1.auth.request.RefreshTokenRequest
import com.calendar.api.presentation.v1.auth.request.TokenRequest
import com.calendar.api.presentation.v1.auth.response.LogoutResponse
import com.calendar.api.presentation.v1.auth.response.TokenResponse
import com.calendar.domain.auth.AuthenticationFacade
import com.calendar.domain.auth.AuthenticationService
import com.calendar.domain.auth.CredentialSocial
import com.calendar.domain.user.SocialType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.util.UriComponentsBuilder

@Tag(name ="Auth API", description = "인증 관련 API")
@ApiV1Controller
class AuthController(
    private val oAuthService: OAuthService,
    private val kakaoProperties: KakaoProperties,
    private val authenticationFacade: AuthenticationFacade,
    private val authenticationService: AuthenticationService
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

    @Operation(summary = "카카오 소셜 로그인", description = "카카오 소셜 로그인합니다.")
    @PostMapping("/auth/social-login/kakao")
    fun socialKakaoLogin(
        @RequestBody request: KakaoCodeRequest
    ):TokenResponse {
        val kakaoAccessToken = oAuthService.getKakaoAccessToken(request.code)
        val kakaoUserInfo = oAuthService.getKakaoUserInfo(kakaoAccessToken.accessToken)

        val token = authenticationFacade.socialLogin(
            CredentialSocial(
                socialType = SocialType.KAKAO,
                socialId = kakaoUserInfo.id,
                nickname = null,
                email = kakaoUserInfo.email,
            )
        )
        return TokenResponse.toResponse(token)
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @PostMapping("/auth/logout")
    fun logout(
        @RequestBody request: TokenRequest
    ): LogoutResponse {
        val logoutKey = authenticationService.logout(request.token)

        return LogoutResponse("${logoutKey}: 로그아웃 되었습니다.")
    }

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    @PostMapping("/auth/reissue")
    fun reissueToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse {
        val token = authenticationService.renew(request.refreshToken)

        return TokenResponse.toResponse(token)
    }
}