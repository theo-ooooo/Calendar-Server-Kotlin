package com.calendar.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun readByUidAndSocialType(uid: String, socialType: SocialType): User.Info? = userRepository.readByUidAndSocialType(uid, socialType)
}