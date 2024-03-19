package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestCheckout
import com.ct467.libmansys.dtos.ResponseCheckout
import com.ct467.libmansys.models.Checkout
import com.ct467.libmansys.models.Employee
import com.ct467.libmansys.models.LibraryCard
import java.time.LocalDate

fun RequestCheckout.toEntity(libraryCard: LibraryCard, employee: Employee): Checkout {
    return Checkout(
        libraryCard = libraryCard,
        employee = employee,
        checkoutDate = LocalDate.now()
    )
}

fun Checkout.toResponse(): ResponseCheckout {
    return ResponseCheckout(
        id = this.id,
        libraryCard = this.libraryCard?.toResponse(),
        employee = this.employee?.toResponse(),
        checkoutDate = this.checkoutDate,
    )
}