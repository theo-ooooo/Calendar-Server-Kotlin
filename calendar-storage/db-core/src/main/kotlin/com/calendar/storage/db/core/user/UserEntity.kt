package com.calendar.storage.db.core.user

import com.calendar.storage.db.core.support.BaseEntity
import com.calendar.domain.user.User
import com.calendar.domain.user.SocialType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "user_key")
    val userKey: String,
    @Column(columnDefinition = "varchar(50)")
    val uid: String,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    val socialType: SocialType,

    val email: String?,
    val nickname: String?,
): BaseEntity() {

    constructor(create: User.Create, createUserKey: User.CreateUserKey) : this(
        uid = create.uid,
        socialType = create.socialType,
        email = create.email,
        nickname = create.nickname,
        userKey = createUserKey.key,
    )

    fun toUser(): User.Info {
        return User.Info(
            id = id!!,
            userKey = userKey,
            socialType = socialType,
            email = email,
            nickname = nickname,
            createdAt = LocalDateTime.now(),
        )
    }
}