package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class RequestAuthor(
    @field:NotEmpty(message = "Username is required")
    val authorName: String,

    @field:Size(max = 255, message = "Website must not exceed 255 characters")
    val website: String,

    @field:Size(max = 255, message = "Note must not exceed 255 characters")
    val note: String,
)

data class ResponseAuthor(
    val id: Long?,
    val authorName: String,
    val website: String,
    val note: String,
    val numberOfBooks: Int
)