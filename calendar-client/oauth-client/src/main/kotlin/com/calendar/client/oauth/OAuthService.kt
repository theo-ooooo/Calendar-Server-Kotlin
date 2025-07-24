package com.calendar.client.oauth

import feign.FeignException
import org.springframework.stereotype.Service

@Service
class OAuthService(
    private val kakaoClient: KakaoClient
) {
    fun getKakaoUserInfo(token: String): KakaoClientResult =
        try {
            kakaoClient.getUserInfo(token)
        } catch (e: FeignException) {
            //TODO: 커스텀 에러 구현때 변경.
            throw IllegalStateException(e)
        }

}