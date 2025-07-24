package com.calendar.client.oauth

class KakaoClientResult {
    data class KakaoUserInfo(
        val id: String,
        val email: String
    )

    data class KakaoAuthToken(
        val accessToken: String,
    )
}
