package com.ct467.libmansys.dtos

import jakarta.validation.constraints.*
import java.time.LocalDate

data class RequestLibraryCard(
    @field:NotNull(message = "Card duration is required")
    @field:Min(value = 1, message = "Card duration must be greater than or equal to 1")
    val cardDuration: Long,

    @field:Size(max = 255, message = "Note must not exceed 255 characters")
    val note: String,
)

data class ResponseLibraryCard(
    val readerId: Long?,
    val cardNumber: Long,
    val startDate: LocalDate,
    val expirationDate: LocalDate,
    val note: String,
)