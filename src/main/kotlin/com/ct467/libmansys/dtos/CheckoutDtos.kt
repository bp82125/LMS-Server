package com.ct467.libmansys.dtos

import com.ct467.libmansys.models.CheckoutDetail
import jakarta.validation.constraints.NotBlank
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