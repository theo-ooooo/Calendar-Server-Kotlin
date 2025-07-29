package com.calendar.storage.db.core.user

import com.calendar.domain.user.SocialType
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByUidAndSocialType(uid: String, socialType: SocialType): UserEntity
}