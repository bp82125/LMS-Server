package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty
import java.time.LocalDate

data class RequestEmployee(
    @field:NotEmpty(message = "Full name is required")
    val fullName: String,

    @field:NotEmpty(message = "Birth date is required")
    val birthDate: LocalDate,

    @field:NotEmpty(message = "Phone number is required")
    val phoneNumber: String
)

data class ResponseEmployee(
    val employeeId: Long,
    val fullName: String,
    val birthDate: LocalDate,
    val phoneNumber: String
)