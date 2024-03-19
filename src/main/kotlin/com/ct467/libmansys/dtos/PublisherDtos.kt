package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class RequestPublisher(
    @field:NotEmpty(message = "Username is required")
    val publisherName: String,

    @field:Size(max = 255, message = "Address must not exceed 255 characters")
    val address: String,

    @field:Size(max = 255, message = "---")
    val email: String,

    @field:Size(max = 255, message = "representativeInfo must not exceed 255 characters")
    val representativeInfo: String
)

data class ResponsePublisher(
    val id: Long?,
    val publisherName: String,
    val address: String,
    val email: String,
    val representativeInfo: String,
    val numberOfBooks: Int
)
