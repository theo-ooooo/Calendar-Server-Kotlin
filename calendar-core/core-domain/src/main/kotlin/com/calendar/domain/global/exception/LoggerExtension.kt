package com.calendar.domain.global.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Lazy<Logger> = lazy { LoggerFactory.getLogger(T::class.java) }