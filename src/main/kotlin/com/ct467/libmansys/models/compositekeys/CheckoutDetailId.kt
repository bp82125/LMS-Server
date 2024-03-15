package com.ct467.libmansys.models.compositekeys

import java.io.Serializable

class CheckoutDetailId(
    var checkout: Long,
    var book: Long
) : Serializable