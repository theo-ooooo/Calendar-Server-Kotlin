package com.calendar.client.oauth

import com.calendar.client.oauth.response.KakaoUserResponse
import org.springframework.stereotype.Component

@Component
 class KakaoClient internal constructor(
     private val kaKaoApi: KaKaoApi
 ){
    fun getUserInfo(token: String): KakaoClientResult = kaKaoApi.getKakaoUserInfo("Bearer $token").toResult()
}