package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class RequestBook(
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotNull(message = "Author ID cannot be null")
    @field:Positive(message = "Author ID must be positive")
    val authorId: Long,

    @field:NotNull(message = "Category ID cannot be null")
    @field:Positive(message = "Category ID must be positive")
    val categoryId: Long,

    @field:NotNull(message = "Publisher ID cannot be null")
    @field:Positive(message = "Publisher ID must be positive")
    val publisherId: Long,

    @field:NotNull(message = "Publication year cannot be null")
    @field:Positive(message = "Publication year must be positive")
    val publicationYear: Int
)

data class ResponseBook(
    val id: Long?,
    val bookName: String,
    val publicationYear: Int,
    val author: ResponseAuthor?,
    val category: ResponseCategory?,
    val publisher: ResponsePublisher?
)