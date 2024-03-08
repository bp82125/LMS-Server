package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestEmployee
import com.ct467.libmansys.dtos.ResponseEmployee
import com.ct467.libmansys.models.Employee

fun RequestEmployee.toEntity(employeeId: Long = 0): Employee {
    return Employee(
        employeeId = employeeId,
        fullName = this.fullName,
        birthDate = this.birthDate,
        phoneNumber = this.phoneNumber
    )
}

fun Employee.toResponse(): ResponseEmployee {
    return ResponseEmployee(
        employeeId = this.employeeId,
        fullName = this.fullName,
        birthDate = this.birthDate,
        phoneNumber = this.phoneNumber
    )
}