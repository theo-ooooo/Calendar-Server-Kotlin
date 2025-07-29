package com.calendar.api.global.jwt

import com.calendar.domain.auth.Provider
import com.calendar.domain.auth.RedisTokenRepository
import com.calendar.domain.global.exception.ErrorException
import com.calendar.domain.global.exception.ErrorType
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames


class JwtConverter(
    private val redisTokenRepository: RedisTokenRepository
): Converter<Jwt, AbstractAuthenticationToken> {
    private var customJwtGrantedAuthoritiesConverter: CustomJwtGrantedAuthoritiesConverter =
        CustomJwtGrantedAuthoritiesConverter()

    private var principalClaimName: String = JwtClaimNames.SUB



    override fun convert(source: Jwt): AbstractAuthenticationToken {
        val authority: Collection<GrantedAuthority> = customJwtGrantedAuthoritiesConverter.convert(source)
        val provider: Provider = findProvider(source)
        return UsernamePasswordAuthenticationToken(provider, null, authority)
    }

    private fun findProvider(jwt: Jwt): Provider {
        val tokenWithAuthentication = redisTokenRepository.findByToken(jwt.tokenValue)

        jwt.validateByCachedToken(tokenWithAuthentication.accessToken)

        return Provider(
            userId = tokenWithAuthentication.provider.userId,
            userKey = tokenWithAuthentication.provider.userKey,
        )
    }

    fun setPrincipalClaimName(name: String) {
        principalClaimName = name
    }

    private fun Jwt.validateByCachedToken(token: String) {
        if (this.tokenValue == token) {
            return
        }

        redisTokenRepository.deleteAllToken(this.tokenValue)
        throw ErrorException(ErrorType.INVALID_TOKEN)
    }


}