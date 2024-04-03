package com.ct467.libmansys.exceptions

import com.ct467.libmansys.dtos.ResponseBook
import com.ct467.libmansys.dtos.ResponseCheckout

class BookNotReturnedException(message: String, val book: ResponseBook, val checkouts: List<ResponseCheckout>): RuntimeException(message)