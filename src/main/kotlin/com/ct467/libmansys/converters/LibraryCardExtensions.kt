package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCardCount
import com.ct467.libmansys.models.LibraryCard
import com.ct467.libmansys.models.Reader
import java.time.LocalDate

fun RequestLibraryCard.toEntity(cardNumber: Long? = null, startDate: LocalDate, expirationDate: LocalDate, reader: Reader?): LibraryCard {
    return LibraryCard(
        cardNumber = cardNumber,
        startDate = startDate,
        expirationDate = expirationDate,
        note = this.note,
        reader = reader
    )
}

fun LibraryCard.toResponse(): ResponseLibraryCard {
    return ResponseLibraryCard(
        cardNumber = this.cardNumber,
        startDate = this.startDate,
        expirationDate = this.expirationDate,
        note = this.note,
        readerId = this.reader?.id,
        deleted = this.deleted
    )
}

fun LibraryCard.toQuantity(): ResponseLibraryCardCount {
    return ResponseLibraryCardCount(
        cardNumber = this.cardNumber,
        numberOfBooks = this.checkouts.fold(0) { acc, checkout -> acc + checkout.countDetails() },
        numberOfCheckouts = this.checkouts.size
    )
}