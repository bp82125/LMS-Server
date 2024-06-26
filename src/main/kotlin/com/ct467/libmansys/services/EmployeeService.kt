package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toQuantity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestEmployee
import com.ct467.libmansys.dtos.ResponseEmployee
import com.ct467.libmansys.dtos.ResponseEmployeeTotal
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.CheckoutRepository
import com.ct467.libmansys.repositories.EmployeeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class EmployeeService(
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val checkoutRepository: CheckoutRepository
) {
    fun countTotal(): ResponseEmployeeTotal {
        val employees = employeeRepository.findAll()
        return ResponseEmployeeTotal(
            employees = employees.map { it.toQuantity() },
            numberOfEmployees = employees.size
        )
    }

    fun findAllEmployees(status: String? = null): List<ResponseEmployee> {
        return when (status) {
            "deleted" -> employeeRepository.findAllByDeletedTrue().map { it.toResponse() }
            "available" -> employeeRepository.findAllByDeletedFalse().map { it.toResponse() }
            "all" -> employeeRepository.findAll().map { it.toResponse() }
            else -> employeeRepository.findAll().map { it.toResponse() }
        }
    }

    fun findEmployeeById(id: Long): ResponseEmployee {
        val employee = employeeRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$id") }

        return employee.toResponse()
    }

    fun createEmployee(requestEmployee: RequestEmployee): ResponseEmployee {
        val employee = requestEmployee.toEntity()
        val createdEmployee = employeeRepository.save(employee)
        return createdEmployee.toResponse()
    }

    fun updateEmployee(id: Long, requestEmployee: RequestEmployee): ResponseEmployee {
        val employee = employeeRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$id") }

        employee.apply {
            this.fullName = requestEmployee.fullName
            this.phoneNumber = requestEmployee.phoneNumber
            this.birthDate = requestEmployee.birthDate
        }

        val updatedEmployee = employeeRepository.save(employee)
        return updatedEmployee.toResponse()
    }

    fun deleteEmployee(id: Long) {
        val employee = employeeRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$id") }

        employee.apply { this.deleted = true }
        employeeRepository.save(employee)
    }
}