package com.calendar.api.global.jwt

import com.calendar.api.global.config.AuthenticationProperties
import com.calendar.domain.auth.AuthorityType
import com.calendar.domain.auth.GrantedAuthority
import com.calendar.domain.auth.Provider
import com.calendar.domain.auth.ProviderDetail
import com.calendar.domain.auth.RedisTokenRepository
import com.calendar.domain.auth.TokenWithAuthentication
import com.calendar.domain.auth.token.Token
import com.calendar.domain.auth.token.TokenRepository
import com.calendar.domain.global.exception.ErrorException
import com.calendar.domain.global.exception.ErrorType
import com.calendar.domain.user.User
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.oauth2.jwt.BadJwtException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.stereotype.Component
import java.time.Instant
import kotlin.jvm.Throws

@Component
class JwtProvider(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder,
    private val authenticationProperties: AuthenticationProperties,
    private val redisTokenRepository: RedisTokenRepository
): TokenRepository {
    companion object {
        val grantedAuthorities = listOf(GrantedAuthority(AuthorityType.USER))
    }

    override fun create(user: User.Info): Token {
        val accessToken =
            issueAccessToken(user.userKey, grantedAuthorities)
        val refreshToken =
            issueRefreshToken(user.userKey)

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
        ).apply {
            redisTokenRepository.create(
                accessToken = this.accessToken,
                refreshToken = this.refreshToken,
                providerDetail =
                    ProviderDetail(
                        userId = user.id,
                        userKey = user.userKey,
                        grantedAuthorities =
                            grantedAuthorities.map {
                                it.authorityType.name
                            }
                    ),
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )
        }
    }

    override fun renew(refreshToken: String): Token {
        val jwt = validateToken(refreshToken)
        val tokenWithAuthentication = redisTokenRepository.findByToken(jwt.tokenValue)
        removeTokens(tokenWithAuthentication.accessToken, tokenWithAuthentication.refreshToken)

        val newAccessToken = issueAccessToken(
            jwtId = tokenWithAuthentication.provider.userKey,
            grantedAuthorities =
                tokenWithAuthentication.provider.grantedAuthorities.map {
                    GrantedAuthority(AuthorityType.valueOf(it))
                }
        )

        val newRefreshToken = issueRefreshToken(
            jwtId = tokenWithAuthentication.provider.userKey,
        )
        return Token(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        ).apply {
            redisTokenRepository.create(
                accessToken = this.accessToken,
                refreshToken = this.refreshToken,
                providerDetail = tokenWithAuthentication.provider,
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )
        }
    }

    override fun remove(token: String): String {
        val jwt = validateToken(token)

        redisTokenRepository.deleteAllToken(jwt.tokenValue)

        return jwt.id
    }

    override fun findByToken(token: String): TokenWithAuthentication = redisTokenRepository.findByToken(token)

    @Throws(ErrorException::class)
    fun validateToken(token: String): Jwt {
        try {
            return jwtDecoder.decode(token)
        } catch (e: BadJwtException) {
            throw ErrorException(ErrorType.INVALID_TOKEN)
        } catch (e: JwtException) {
            throw AuthenticationServiceException(e.message, e)
        }
    }

    private fun issueAccessToken(
        jwtId: String,
        grantedAuthorities: List<GrantedAuthority>,
    ): String {
        val issuedAt: Instant = Instant.now()
        return generateToken(
            jwtId = jwtId,
            expiresAt = issuedAt.plusSeconds(authenticationProperties.accessTokenExpirationSeconds * 60L),
            issuedAt = issuedAt,
            claims = mapOf(Pair("type", "A"), Pair("roles", grantedAuthorities.map { it.authorityType.name })),
        )
    }

    private fun issueRefreshToken(jwtId: String): String {
        val issuedAt: Instant = Instant.now()
        return generateToken(
            jwtId = jwtId,
            expiresAt = issuedAt.plusSeconds(authenticationProperties.refreshTokenExpirationSeconds * 60L),
            issuedAt = issuedAt,
            claims = mapOf(Pair("type", "R"))
        )
    }


    private fun generateToken(
        jwtId: String,
        expiresAt: Instant,
        issuedAt: Instant,
        claims: Map<String, Any> = emptyMap(),
    ):String {
        val jwtClaimsSet: JwtClaimsSet =
            JwtClaimsSet
                .builder()
                .id(jwtId)
                .expiresAt(expiresAt)
                .issuedAt(issuedAt)
                .claims {
                    it.putAll(claims)
                }.build()
        try {
            return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).tokenValue
        }catch(e: IllegalArgumentException) {
            throw InvalidBearerTokenException(e.message)
        }
    }

    private fun removeTokens(accessToken:String, refreshToken:String) {
        redisTokenRepository.deleteToken(accessToken)
        redisTokenRepository.deleteToken(refreshToken)
    }
}