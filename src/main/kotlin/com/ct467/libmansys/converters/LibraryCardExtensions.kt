package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.models.LibraryCard
import com.ct467.libmansys.models.Reader
import java.time.LocalDate

fun RequestLibraryCard.toEntity(cardNumber: Long = 2, startDate: LocalDate, reader: Reader): LibraryCard {
    return LibraryCard(
        cardNumber = cardNumber,
        startDate = startDate,
        expirationDate = startDate.plusMonths(this.cardDuration),
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
    )
}