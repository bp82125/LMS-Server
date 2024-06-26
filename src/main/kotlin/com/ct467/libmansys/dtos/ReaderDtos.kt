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
    val id: Long?,
    val readerName: String,
    val address: String,
    val libraryCard: ResponseLibraryCard?,
    val deleted: Boolean
)

data class ResponseReaderCount(
    val id: Long?,
    val readerName: String,
    val numberOfBooks: Int?,
    val numberOfCheckouts: Int?
)

data class ResponseReaderTotal(
    val readers: List<ResponseReaderCount>,
    val numberOfReaders: Int
)