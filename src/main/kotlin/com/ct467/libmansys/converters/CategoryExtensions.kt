package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestCategory
import com.ct467.libmansys.dtos.ResponseCategory
import com.ct467.libmansys.models.Category

fun RequestCategory.toEntity(id: Long = 0): Category {
    return Category(
        categoryId = id,
        categoryName = this.categoryName,
    )
}

fun Category.toResponse(): ResponseCategory {
    return ResponseCategory(
        id = this.categoryId,
        categoryName = this.categoryName
    )
}