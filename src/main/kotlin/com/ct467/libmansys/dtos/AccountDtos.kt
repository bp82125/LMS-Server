package com.ct467.libmansys.dtos

import com.ct467.libmansys.enums.AccountRole
import com.ct467.libmansys.models.Account
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CreateAccount(
    @field:NotBlank(message = "Username must not be blank")
    var username: String,

    @field:NotBlank(message = "Password must not be blank")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,}\$",
        message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    var password: String,

    @field:NotNull(message = "Role must not be null")
    var role: AccountRole,

    @field:NotNull(message = "Account state must not be null")
    var enabled: Boolean,
)

data class UpdateRoleAccount(
    @field:NotNull(message = "Role must not be null")
    var role: AccountRole,
)

data class ToggleAccount(
    @field:NotNull(message = "Account state must not be null")
    var enabled: Boolean,
)

data class ResetPasswordAccount(
    @field:NotBlank(message = "New password must not be blank")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,}\$",
        message = "New password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    var newPassword: String
)

data class ResponseAccount(
    val id: Long?,
    val username: String,
    val role: AccountRole,
    val enabled: Boolean,
    val employee: ResponseEmployee?
)

class AccountPrincipal(
    private val account: Account
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(account.role.name))
    }

    override fun getPassword(): String = account.password

    override fun getUsername(): String = account.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = account.enabled

    fun getAccount(): Account = this.account

}