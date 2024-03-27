package com.ct467.libmansys.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class JwtProvider(
    @Autowired private val jwtEncoder: JwtEncoder
) {
    fun createToken(authentication: Authentication): String {
        val now = Instant.now()
        val expiresIn: Long = 2

        val authorities = authentication.authorities.joinToString(" ") { it.authority }

        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(expiresIn, ChronoUnit.HOURS))
            .subject(authentication.name)
            .claim("authorities", authorities)
            .build()

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}