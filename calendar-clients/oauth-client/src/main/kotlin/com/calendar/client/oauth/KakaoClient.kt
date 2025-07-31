package com.calendar.client.oauth

import org.springframework.stereotype.Component

@Component
 class KakaoClient internal constructor(
     private val kaKaoUserApi: KaKaoUserApi,
     private val kakaoAuthApi: KakaoAuthApi
 ){
    fun getUserInfo(token: String): KakaoClientResult.KakaoUserInfo =
        kaKaoUserApi.getKakaoUserInfo("Bearer $token").toResult()
    fun getAccessToken(grantType: String, clientId: String, redirectUri: String, code: String): KakaoClientResult.KakaoAuthToken =
        kakaoAuthApi.getAccessToken(grantType, clientId, redirectUri, code).toResult()

}