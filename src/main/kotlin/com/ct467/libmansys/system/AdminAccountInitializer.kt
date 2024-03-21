package com.ct467.libmansys.system

import com.ct467.libmansys.dtos.CreateAccount
import com.ct467.libmansys.dtos.RequestEmployee
import com.ct467.libmansys.enums.AccountRole
import com.ct467.libmansys.services.AccountService
import com.ct467.libmansys.services.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AdminAccountInitializer(
    @Autowired private val employeeService: EmployeeService,
    @Autowired private val accountService: AccountService
): CommandLineRunner {
    override fun run(vararg args: String?) {
        if (employeeService.findAllEmployees().isEmpty()){
            val admin = RequestEmployee(
                fullName = "Administrator",
                birthDate = LocalDate.now(),
                phoneNumber = "0000000000"
            )

            val createdAdmin = employeeService.createEmployee(admin)

            val account = CreateAccount(
                username = "admin",
                password = "Admin123@",
                enabled = true,
                role = AccountRole.ADMIN
            )

            val createdAccount = createdAdmin.id?.let { accountService.createAccount(employeeId = it, createAccount = account) }
            println("Admin account created successfully")
        } else {
            println("Admin account already exists")
        }
    }

}