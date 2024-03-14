package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestEmployee
import com.ct467.libmansys.dtos.ResponseEmployee
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.EmployeeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class EmployeeService(
    @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findAllEmployees(): List<ResponseEmployee> {
        return employeeRepository
            .findAll()
            .map { employee -> employee.toResponse() }
    }

    fun findEmployeeById(employeeId: Long): ResponseEmployee {
        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = employeeId) }

        return employee.toResponse()
    }

    fun createEmployee(requestEmployee: RequestEmployee): ResponseEmployee {
        val employee = requestEmployee.toEntity()
        val createdEmployee = employeeRepository.save(employee)
        return createdEmployee.toResponse()
    }

    fun updateEmployee(employeeId: Long, requestEmployee: RequestEmployee): ResponseEmployee {
        if (!employeeRepository.existsById(employeeId)) {
            throw EntityWithIdNotFoundException("Employee", employeeId)
        }

        val employee = requestEmployee.toEntity(employeeId)
        val updatedEmployee = employeeRepository.save(employee)
        return updatedEmployee.toResponse()
    }

    fun deleteEmployee(employeeId: Long) {
        if (!employeeRepository.existsById(employeeId)) {
            throw EntityWithIdNotFoundException("Employee", employeeId)
        }

        return employeeRepository.deleteById(employeeId)
    }
}