package com.ct467.libmansys.dtos

import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class RequestCheckout(
    @field:NotNull(message = "Card number must not be null")
    val cardNumber: Long,

    @field:NotNull(message = "Librarian ID must not be null")
    val employeeId: Long,
)

data class ResponseCheckout(
    val id: Long?,
    val libraryCard: ResponseLibraryCard?,
    val employee: ResponseEmployee?,
    val checkoutDate: LocalDate,
    val returnedAll: Boolean
)

data class ResponseCheckoutCount(
    val id: Long?,
    val numberOfDetails: Int
)

data class ResponseCheckoutTotal(
    val checkouts: List<ResponseCheckoutCount>,
    val numberOfCheckouts: Int
)