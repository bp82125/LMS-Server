package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty

data class RequestReader(
    @field:NotEmpty(message = "Reader name is required")
    val readerName: String,

    @field:NotEmpty(message = "Address is required")
    val address: String,

    val libraryCardNumber: Long? = null
)

data class ResponseReader(
    val readerId: Long,
    val readerName: String,
    val address: String,
    val libraryCard: ResponseLibraryCard?
)