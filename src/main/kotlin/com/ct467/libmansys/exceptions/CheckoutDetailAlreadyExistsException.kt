package com.ct467.libmansys.exceptions

class CheckoutDetailAlreadyExistsException(checkoutId: Long, bookId: Long)
    : RuntimeException("A CheckoutDetail already exists for Checkout ID $checkoutId and Book ID $bookId")
