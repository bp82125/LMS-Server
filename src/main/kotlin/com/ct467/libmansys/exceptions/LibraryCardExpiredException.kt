package com.ct467.libmansys.exceptions

class LibraryCardExpiredException(cardNumber: Long) : RuntimeException(
    "Library card with number $cardNumber is expired and cannot be used to borrow books."
)
