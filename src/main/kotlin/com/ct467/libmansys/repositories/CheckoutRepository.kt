package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Checkout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CheckoutRepository : JpaRepository<Checkout, Long> {
    fun countByEmployeeId(employeeId: Long): Long

    @Query(
        "SELECT cd.checkout FROM CheckoutDetail cd " +
                "WHERE cd.checkout.libraryCard.cardNumber = :cardNumber " +
                "AND cd.book.id = :bookId AND cd.returned = false")
    fun findCheckoutsByCardNumberAndBookIdAndNotReturned(cardNumber: Long, bookId: Long): List<Checkout>
}