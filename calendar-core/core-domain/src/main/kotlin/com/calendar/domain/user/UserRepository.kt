package com.calendar.domain.user

interface UserRepository {
    fun findByUidAndProvider(uid:String, provider: UserProvider): User.Info
}