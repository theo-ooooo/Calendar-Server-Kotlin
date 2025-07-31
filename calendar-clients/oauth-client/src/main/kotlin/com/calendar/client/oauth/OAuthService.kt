package com.calendar.client.oauth

import com.calendar.client.oauth.config.KakaoProperties
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class OAuthService(
    private val kakaoClient: KakaoClient,
    private val kakaoProperties: KakaoProperties
) {
    fun getKakaoUserInfo(token: String): KakaoClientResult.KakaoUserInfo =
        try {
            kakaoClient.getUserInfo(token)
        } catch (e: FeignException) {
            //TODO: 커스텀 에러 구현때 변경.
            throw IllegalStateException(e)
        }

    fun getKakaoAccessToken(code: String): KakaoClientResult.KakaoAuthToken =
        kakaoClient.getAccessToken("authorization_code", kakaoProperties.clientId, kakaoProperties.redirectUri, code)
}