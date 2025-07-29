package com.calendar.domain.user

import java.time.LocalDateTime

class User {
    data class Create (
        val uid: String,
        val socialType: SocialType,
        val email: String?,
        val nickname: String?
    )

    data class CreateUserKey(
        val key: String,
    )

    data class Info(
        val id: Long,
        val userKey: String,
        val socialType: SocialType,
        val email: String?,
        val nickname: String?,
        val createdAt: LocalDateTime,
    )
}