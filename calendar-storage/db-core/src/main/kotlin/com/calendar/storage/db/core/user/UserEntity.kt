package com.calendar.storage.db.core.user

import com.calendar.storage.db.core.support.BaseEntity
import com.calendar.user.User
import com.calendar.user.UserProvider
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "users")
class UserEntity(
    @Column(columnDefinition = "varchar(50)")
    val uid: String,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    val provider: UserProvider,

    val email: String?,
    val nickname: String?,
): BaseEntity() {

    constructor(create: User.Create) : this(
        uid = create.uid,
        provider = create.provider,
        email = create.email,
        nickname = create.nickname,
    )

    fun toUser(): User.Info {
        return User.Info(
            id = id!!,
            provider = provider,
            email = email,
            nickname = nickname,
            createdAt = LocalDateTime.now(),
        )
    }
}