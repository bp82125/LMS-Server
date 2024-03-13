package com.ct467.libmansys.system

data class ApiResponse<T>(
    val flag: Boolean,
    val statusCode: Int,
    val data: Any? = null,
    val message: String? = "",
    val error: Any? = null
)