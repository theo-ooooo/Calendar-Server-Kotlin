package com.calendar.domain.auth

import com.calendar.domain.user.SocialType

data class CredentialSocial(
    val email: String?,
    val nickname: String?,
    val socialType: SocialType,
    val socialId: String,
)
