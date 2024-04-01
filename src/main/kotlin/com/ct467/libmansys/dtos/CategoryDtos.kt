package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class RequestCategory(
    @field:NotEmpty(message = "Username is required")
    val categoryName: String,
)

data class ResponseCategory(
    val id: Long?,
    val categoryName: String,
    val numberOfBooks: Int
)

data class ResponseCategoryTotal(
    val categories: List<ResponseCategory>,
    val numberOfCategories: Int
)