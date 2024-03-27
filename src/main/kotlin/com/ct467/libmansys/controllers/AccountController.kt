package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.*
import com.ct467.libmansys.services.AccountService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.endpoint.base-url}/employees")
class AccountController (
    @Autowired private val accountService: AccountService
) {
    @GetMapping("/accounts", "/accounts/")
    fun findAllAccounts(): ResponseEntity<ApiResponse<List<ResponseAccount>>> {
        val accounts = accountService.findAllAccounts()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = accounts,
                message = "Found accounts"
            )
        )
    }

    @GetMapping("/{employeeId}/accounts", "/{employeeId}/accounts/")
    fun findAccountByEmployeeId(
        @PathVariable employeeId: Long
    ): ResponseEntity<ApiResponse<ResponseAccount>> {
        val account = accountService.findAccountByEmployeeId(employeeId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Found account of employee with id: $employeeId"
            )
        )
    }

    @PostMapping("/{employeeId}/accounts", "/{employeeId}/accounts/")
    fun createAccount(
        @PathVariable employeeId: Long,
        @Valid @RequestBody createAccount: CreateAccount
    ): ResponseEntity<ApiResponse<ResponseAccount>> {
        val account = accountService.createAccount(employeeId, createAccount)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Created account for employee with id: $employeeId"
            )
        )
    }

    @PatchMapping("/{employeeId}/accounts/role", "/{employeeId}/accounts/role/")
    fun changeRole(
        @PathVariable employeeId: Long,
        @Valid @RequestBody updateRoleAccount: UpdateRoleAccount
    ): ResponseEntity<ApiResponse<ResponseAccount>> {
        val account = accountService.changeRole(employeeId, updateRoleAccount)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Changed role for account of employee with id: $employeeId"
            )
        )
    }

    @PatchMapping("/{employeeId}/accounts/toggle", "/{employeeId}/accounts/toggle/")
    fun toggleAccount(
        @PathVariable employeeId: Long,
        @Valid @RequestBody toggleAccount: ToggleAccount
    ): ResponseEntity<ApiResponse<ResponseAccount>> {
        val account = accountService.toggleAccount(employeeId, toggleAccount)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Toggled account of employee with id: $employeeId"
            )
        )
    }

    @PatchMapping("/{employeeId}/accounts/password-reset", "/{employeeId}/accounts/password-reset/")
    fun resetPassword(
        @PathVariable employeeId: Long,
        @Valid @RequestBody passwordAccount: ResetPasswordAccount
    ): ResponseEntity<ApiResponse<ResponseAccount>> {
        val account = accountService.resetPassword(employeeId, passwordAccount)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Password reset successfully for account of employee with id: $employeeId"
            )
        )
    }

    @DeleteMapping("/{employeeId}/accounts", "/{employeeId}/accounts/")
    fun deleteAccount(
        @PathVariable employeeId: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        accountService.deleteAccount(employeeId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Account deleted successfully"
            )
        )
    }
}