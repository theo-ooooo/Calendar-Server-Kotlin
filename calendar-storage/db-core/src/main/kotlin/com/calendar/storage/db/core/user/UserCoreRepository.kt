package com.calendar.storage.db.core.user

import com.calendar.domain.user.User
import com.calendar.domain.user.UserProvider
import com.calendar.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun findByUidAndProvider(uid: String, provider: UserProvider): User.Info {
        return userJpaRepository.findByUidAndProvider(uid, provider).toUser()
    }
}