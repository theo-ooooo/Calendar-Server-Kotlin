package com.calendar.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val userKeyGenerator: UserKeyGenerator
) {

    fun create(newUser: User.Create): User.Info {
        val newUserKey = userKeyGenerator.generate()
        return userRepository.create(
            newUser = newUser,
            newUserKey = newUserKey,
        )
    }
}