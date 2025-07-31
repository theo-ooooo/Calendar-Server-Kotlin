package com.calendar.domain.auth

import com.calendar.domain.auth.token.Token
import com.calendar.domain.user.User
import com.calendar.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class AuthenticationFacade(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) {
    fun socialLogin(credentialSocial: CredentialSocial): Token {
        val user = userService.findByUidAndSocialType(
            uid = credentialSocial.socialId,
            socialType = credentialSocial.socialType,
        )

        val socialUser =
            user
                ?: userService.create(
                    User.Create(
                        uid = credentialSocial.socialId,
                        socialType = credentialSocial.socialType,
                        email = credentialSocial.email,
                        nickname = credentialSocial.nickname,
                    )
                )

        return authenticationService.socialLogin(socialUser)
    }
}