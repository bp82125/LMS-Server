package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.AccountPrincipal
import com.ct467.libmansys.dtos.CreateAccount
import com.ct467.libmansys.dtos.ResponseAccount
import com.ct467.libmansys.models.Account
import com.ct467.libmansys.models.Employee
import org.springframework.security.core.userdetails.UserDetails

fun CreateAccount.toEntity(employee: Employee): Account {
    return Account(
        username = this.username,
        password = this.password,
        role = this.role,
        enabled = this.enabled,
        employee = employee
    )
}

fun Account.toResponse(): ResponseAccount {
    return ResponseAccount(
        id = this.id,
        username = this.username,
        role = this.role,
        enabled = this.enabled,
        employee = this.employee?.toResponse()
    )
}

fun Account.toUserDetails(): AccountPrincipal {
    return AccountPrincipal(this)
}