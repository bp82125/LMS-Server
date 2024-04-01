package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.converters.toUserDetails
import com.ct467.libmansys.dtos.*
import com.ct467.libmansys.exceptions.*
import com.ct467.libmansys.repositories.AccountRepository
import com.ct467.libmansys.repositories.EmployeeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class AccountService(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
): UserDetailsService {

    fun countTotal(): Int {
        return accountRepository.findAll().count()
    }

    fun findAllAccounts(): List<ResponseAccount> {
        return accountRepository.findAll().map { it.toResponse() }
    }

    fun findAccountByEmployeeId(employeeId: Long): ResponseAccount {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Account", id = "$employeeId") }

        val account = employee.account
            ?: throw AssociatedEntityNotFoundException(entityName =  "Account", association =  "Employee", id = employeeId.toString())

        return account.toResponse()
    }

    fun createAccount(employeeId: Long, createAccount: CreateAccount): ResponseAccount {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$employeeId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Account", id = "$employeeId") }

        if(employee.account != null){
            throw EntityAlreadyAssociatedException(
                entityName = "Employee",
                entityId = "$employeeId",
                associatedEntityName = "Account",
                associatedEntityId = "${employee.account!!.id}"
            )
        }

        if (accountRepository.existsByUsername(createAccount.username)) {
            throw UsernameAlreadyExistsException(createAccount.username)
        }

        val account = createAccount
            .toEntity(employee = employee)
            .apply {
                this.password = passwordEncoder.encode(this.password)
            }

        val createdAccount = accountRepository.save(account)

        employee
            .apply { this.account = createdAccount }
            .let { employeeRepository.save(it) }

        return createdAccount.toResponse()
    }

    fun changeRole(employeeId: Long, updateRoleAccount: UpdateRoleAccount): ResponseAccount {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$employeeId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Account", id = "$employeeId") }

        val account = employee.account
            ?: throw AssociatedEntityNotFoundException(entityName =  "Account", association =  "Employee", id = employeeId.toString())


        account.apply { this.role = updateRoleAccount.role }
        return accountRepository.save(account).toResponse()
    }

    fun resetPassword(employeeId: Long, passwordAccount: ResetPasswordAccount): ResponseAccount {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$employeeId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Account", id = "$employeeId") }

        val account = employee.account
            ?: throw AssociatedEntityNotFoundException(entityName =  "Account", association =  "Employee", id = employeeId.toString())

        account.password = passwordEncoder.encode(passwordAccount.newPassword)
        return accountRepository.save(account).toResponse()
    }

    fun toggleAccount(employeeId: Long, toggleAccount: ToggleAccount): ResponseAccount {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$employeeId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Account", id = "$employeeId") }

        val account = employee.account
            ?: throw AssociatedEntityNotFoundException(entityName =  "Account", association =  "Employee", id = employeeId.toString())

        account.enabled = toggleAccount.enabled
        return accountRepository.save(account).toResponse()
    }


    fun deleteAccount(employeeId: Long){
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Account", id = "$employeeId") }

        val account = employee.account
            ?: throw AssociatedEntityNotFoundException(entityName =  "Account", association =  "Employee", id = employeeId.toString())

        if(account.username == "admin") {
            throw AdminAccountDeletionException()
        }

        employee.removeAccount()
        employeeRepository.save(employee)

        account.id?.let { accountRepository.deleteById(it) }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository
            .findByUsername(username)
            .map { it.toUserDetails() }
            .orElseThrow { UsernameNotFoundException("Username $username is not found") }
    }

}