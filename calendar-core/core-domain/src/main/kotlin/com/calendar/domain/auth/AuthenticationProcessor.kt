package com.calendar.domain.auth

import com.calendar.domain.auth.token.TokenRepository
import com.calendar.domain.user.User
import org.springframework.stereotype.Component

@Component
class AuthenticationProcessor(
    private val tokenRepository: TokenRepository,
) {

    fun login(
        user: User.Info
    ) =
        tokenRepository.create(user)


    fun renew(refreshToken: String) = tokenRepository.renew(refreshToken)

    fun logout(token: String) = tokenRepository.remove(token)
}