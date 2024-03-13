package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class RequestEmployee(
    @field:NotEmpty(message = "Full name is required")
    val fullName: String,

    @field:NotNull(message = "Birth date is required")
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