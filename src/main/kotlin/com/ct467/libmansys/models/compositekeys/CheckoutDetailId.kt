package com.ct467.libmansys.models.compositekeys

import java.io.Serializable

class CheckoutDetailId(
    var checkout: Long? = null,
    var book: Long? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CheckoutDetailId) return false

        if (checkout != other.checkout) return false
        if (book != other.book) return false

        return true
    }

    override fun hashCode(): Int {
        var result = checkout.hashCode()
        result = 31 * result + book.hashCode()
        return result
    }

    override fun toString(): String {
        return "CheckoutDetailId(checkout=$checkout, book=$book)"
    }
}