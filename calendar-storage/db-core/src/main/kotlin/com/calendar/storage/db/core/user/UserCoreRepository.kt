package com.calendar.storage.db.core.user

import com.calendar.domain.user.User
import com.calendar.domain.user.SocialType
import com.calendar.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun findByUidAndProvider(uid: String, socialType: SocialType): User.Info {
        return userJpaRepository.findByUidAndProvider(uid, socialType).toUser()
    }

    override fun create(
        newUser: User.Create,
        newUserKey: User.CreateUserKey
    ): User.Info {
        TODO("Not yet implemented")
    }
}