package com.calendar.domain.user

import java.time.LocalDateTime

class User {
    data class Create (
        val uid: String,
        val provider: UserProvider,
        val email: String?,
        val nickname: String?
    )

    data class Info(
        val id: Long,
        val provider: UserProvider,
        val email: String?,
        val nickname: String?,
        val createdAt: LocalDateTime,
    )
}