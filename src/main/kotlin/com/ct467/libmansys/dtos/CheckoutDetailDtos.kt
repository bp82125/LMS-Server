package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class RequestCheckoutDetail(
    val note: String?,
    val returned: Boolean = false
)

data class ResponseCheckoutDetail(
    val checkoutId: Long,
    val book: ResponseBook,
    val note: String,
    val returned: Boolean,
    val returnDate: LocalDate?
)