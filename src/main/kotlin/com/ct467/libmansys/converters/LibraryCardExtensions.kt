package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.models.LibraryCard

fun RequestLibraryCard.toEntity(cardNumber: Long = 0): LibraryCard {
    return LibraryCard(
        cardNumber = cardNumber,
        startDate = this.startDate,
        expirationDate = this.expirationDate,
        note = this.note
    )
}

fun LibraryCard.toResponse(): ResponseLibraryCard {
    return ResponseLibraryCard(
        cardNumber = this.cardNumber,
        startDate = this.startDate,
        expirationDate = this.expirationDate,
        note = this.note
    )
}