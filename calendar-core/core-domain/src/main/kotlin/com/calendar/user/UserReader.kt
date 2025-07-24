package com.calendar.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun readByUidAndProvider(uid: String, provider: UserProvider): User.Info = userRepository.findByUidAndProvider(uid, provider)
}