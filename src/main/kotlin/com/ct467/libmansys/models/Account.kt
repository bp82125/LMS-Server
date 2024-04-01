package com.ct467.libmansys.models

import com.ct467.libmansys.enums.AccountRole
import jakarta.persistence.*

@Entity
@Table(name = "accounts")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "username", nullable = false, unique = true)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: AccountRole,

    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = true,

    @OneToOne(
        mappedBy = "account",
        fetch = FetchType.LAZY,
        targetEntity = Employee::class
    )
    var employee: Employee? = null
) {
    fun isAdmin(): Boolean {
        return role == AccountRole.ADMIN
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (role != other.role) return false
        if (enabled != other.enabled) return false


        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + (employee?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Account(id=$id, username='$username', password='$password', role=$role, enabled=$enabled, employee=$employee)"
    }


}