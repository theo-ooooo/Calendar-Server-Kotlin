package com.calendar.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader
) {
    fun findByUidAndProvider(uid: String, socialType: SocialType): User.Info = userReader.readByUidAndProvider(uid, socialType)
}