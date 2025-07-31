package com.calendar.client.oauth.response

import com.calendar.client.oauth.KakaoClientResult
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserResponse(
    val id: String,
    @field:JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount,
) {
    fun toResult(): KakaoClientResult.KakaoUserInfo =
        KakaoClientResult.KakaoUserInfo(
            id = id,
            email = kakaoAccount.email ?: "",
        )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoAccount(
    val email: String?,
    val name: String?,
    @field:JsonProperty("profile")
    val kakaoProfile: KakaoProfile,
    @field:JsonProperty("phone_number")
    val phoneNumber: String?,
    @field:JsonProperty("birthyear")
    val birthYear: String?,
    @field:JsonProperty("birthday")
    val birthDay: String?,
    val gender: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoProfile(
    val nickname: String?,
)
