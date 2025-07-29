package com.calendar.domain.user

interface UserRepository {
    fun findByUidAndSocialType(uid:String, socialType: SocialType): User.Info

    fun create(
        newUser: User.Create,
        newUserKey: User.CreateUserKey
    ): User.Info
}