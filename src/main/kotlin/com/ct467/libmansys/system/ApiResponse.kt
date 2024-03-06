package com.ct467.libmansys.system

data class ApiResponse<T>(
    val flag: Boolean,
    val statusCode: Int,
    val data: T? = null,
    val message: String? = ""
)