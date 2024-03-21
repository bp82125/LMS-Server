package com.ct467.libmansys.security

import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.AccountPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Autowired private val jwtProvider: JwtProvider
) {
    fun createLoginInfo(authentication: Authentication): Map<String, Any> {
        val accountPrincipal = authentication.principal as AccountPrincipal
        val account = accountPrincipal.getAccount()
        val responseAccount = account.toResponse()

        val token = jwtProvider.createToken(authentication)

        return mapOf(
            "accountInfo" to responseAccount,
            "token" to token
        )
    }
}