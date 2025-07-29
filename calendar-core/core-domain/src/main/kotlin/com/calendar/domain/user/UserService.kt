package com.calendar.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader
) {
    fun findByUidAndSocialType(uid: String, socialType: SocialType): User.Info = userReader.readByUidAndSocialType(uid, socialType)
}