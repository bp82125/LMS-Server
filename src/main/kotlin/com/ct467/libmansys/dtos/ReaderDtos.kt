package com.ct467.libmansys.dtos

import com.ct467.libmansys.models.LibraryCard
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class RequestReader(
    @field:NotEmpty(message = "Reader name is required")
    val readerName: String,

    @field:NotEmpty(message = "Address is required")
    val address: String,

    @field:NotNull(message = "Library ID cannot be null")
    @field:Positive(message = "Library ID must be positive")
    val libraryCardNumber: Long
)

data class ResponseReader(
    val readerId: Long,
    val readerName: String,
    val address: String,
    val libraryCardNumber: ResponseLibraryCard
)