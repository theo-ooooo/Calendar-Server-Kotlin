package com.calendar.storage.db.core.user

import com.calendar.domain.user.User
import com.calendar.domain.user.SocialType
import com.calendar.domain.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
@Transactional
class UserCoreRepository(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun readByUidAndSocialType(uid: String, socialType: SocialType): User.Info? {
        return userJpaRepository.readByUidAndSocialType(uid, socialType)
            ?.toUser()
    }


    override fun create(
        newUser: User.Create,
        newUserKey: User.CreateUserKey
    ): User.Info =
        userJpaRepository.save(UserEntity(create = newUser, createUserKey = newUserKey)).toUser()

}