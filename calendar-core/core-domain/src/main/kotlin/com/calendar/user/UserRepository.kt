package com.calendar.user

interface UserRepository {
    fun findByUidAndProvider(uid:String, provider: UserProvider): User.Info
}