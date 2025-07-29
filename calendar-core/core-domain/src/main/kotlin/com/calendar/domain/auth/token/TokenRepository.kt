package com.calendar.domain.auth.token

import com.calendar.domain.auth.Provider
import com.calendar.domain.auth.TokenWithAuthentication
import com.calendar.domain.user.User

interface TokenRepository {
    fun create(
        user: User.Info
    ): Token

    fun renew(refreshToken: String): Token

    fun remove(token: String): String

    fun findByToken(token: String): TokenWithAuthentication
}