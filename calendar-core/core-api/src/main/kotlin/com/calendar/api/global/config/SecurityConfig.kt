package com.calendar.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        objectMapper: ObjectMapper): SecurityFilterChain {

        http
            .cors {  }
            .headers { it.frameOptions { option -> option.disable() } }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper)) }

        http.authorizeHttpRequests { authorize ->
            authorize.anyRequest().authenticated()
        }

        return http.build()
    }

}