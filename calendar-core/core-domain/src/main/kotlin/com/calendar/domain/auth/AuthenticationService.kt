package com.calendar.domain.auth

import com.calendar.domain.auth.token.Token
import com.calendar.domain.user.User
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val authenticationProcessor: AuthenticationProcessor
) {

    fun socialLogin(user: User.Info): Token =
        authenticationProcessor.login(user)


    fun logout(token: String): String =
        authenticationProcessor.logout(token)

    fun renew(refreshToken: String): Token =
        authenticationProcessor.renew(refreshToken)
}