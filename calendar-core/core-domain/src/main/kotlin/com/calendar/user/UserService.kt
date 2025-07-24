package com.calendar.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader
) {
    fun findByUidAndProvider(uid: String, provider: UserProvider): User.Info = userReader.readByUidAndProvider(uid, provider)
}