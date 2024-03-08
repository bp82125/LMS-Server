package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class RequestLibraryCard(

    @field:NotEmpty(message = "Start date is required")
    val startDate: LocalDate,

    @field:NotEmpty(message = "Expiration date is required")
    val expirationDate: LocalDate,

    @field:Size(max = 255, message = "Note must not exceed 255 characters")
    val note: String
)

data class ResponseLibraryCard(
    val cardNumber: Long,
    val startDate: LocalDate,
    val expirationDate: LocalDate,
    val note: String
)