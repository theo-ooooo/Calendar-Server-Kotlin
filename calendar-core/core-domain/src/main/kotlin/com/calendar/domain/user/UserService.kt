package com.calendar.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader,
    private val userAppender: UserAppender
) {
    fun create(user: User.Create): User.Info =
        userAppender.create(user)


    fun findByUidAndSocialType(uid: String, socialType: SocialType): User.Info? = userReader.readByUidAndSocialType(uid, socialType)
}