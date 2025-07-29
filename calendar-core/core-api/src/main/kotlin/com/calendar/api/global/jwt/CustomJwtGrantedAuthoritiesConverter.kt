package com.calendar.api.global.jwt

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class CustomJwtGrantedAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {
    override fun convert(source: Jwt): Collection<GrantedAuthority> {
        val getClaim = source.getClaim<List<String>>("roles") ?: return emptyList()

        return getClaim.map { role -> SimpleGrantedAuthority(role) }

    }
}