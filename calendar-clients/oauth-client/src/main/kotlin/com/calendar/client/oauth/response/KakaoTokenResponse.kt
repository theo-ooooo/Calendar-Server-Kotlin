package com.calendar.client.oauth.response

import com.calendar.client.oauth.KakaoClientResult
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoTokenResponse(
    @field:JsonProperty("access_token")
    val accessToken: String,
) {
    fun toResult(): KakaoClientResult.KakaoAuthToken =
        KakaoClientResult.KakaoAuthToken(
            accessToken = accessToken,
        )
}
