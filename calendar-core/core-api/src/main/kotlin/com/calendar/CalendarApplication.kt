package com.calendar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class CalendarApplication

fun main(args: Array<String>) {
    runApplication<CalendarApplication>(*args)
}