package com.calendar.storage.redis.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis")
data class RedisProperties(
    var host: String,
    var port: Int,
    var password: String?,
)
