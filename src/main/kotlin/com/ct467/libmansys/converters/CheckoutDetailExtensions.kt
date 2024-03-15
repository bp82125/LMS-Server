package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestCheckoutDetail
import com.ct467.libmansys.dtos.ResponseCheckoutDetail
import com.ct467.libmansys.models.Book
import com.ct467.libmansys.models.Checkout
import com.ct467.libmansys.models.CheckoutDetail

fun CheckoutDetail.toResponse(): ResponseCheckoutDetail {
    return ResponseCheckoutDetail(
        checkoutId = this.checkout.id,
        book = this.book.toResponse(),
        note = this.note,
        returned = this.returned,
        returnDate = this.returnDate
    )
}

fun RequestCheckoutDetail.toEntity(checkout: Checkout, book: Book): CheckoutDetail {
    return CheckoutDetail(
        checkout = checkout,
        book = book,
        note = this.note ?: "",
        returned = this.returned
    )
}