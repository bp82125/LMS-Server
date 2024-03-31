package com.ct467.libmansys.dtos

import jakarta.validation.constraints.*
import java.time.LocalDate

data class RequestLibraryCard(
    @field:NotNull(message = "Card duration is required")
    @field:Min(value = 0, message = "Card duration must be greater than or equal to 0")
    val cardDuration: Long,

    @field:Size(max = 255, message = "Note must not exceed 255 characters")
    val note: String,
)

data class ResponseLibraryCard(
    val cardNumber: Long?,
    val startDate: LocalDate,
    val expirationDate: LocalDate,
    val note: String,
    val readerId: Long?,
    val deleted: Boolean
)