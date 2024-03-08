package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestReader
import com.ct467.libmansys.dtos.ResponseReader
import com.ct467.libmansys.models.LibraryCard
import com.ct467.libmansys.models.Reader

fun RequestReader.toEntity(id: Long = 0, libraryCard: LibraryCard): Reader {
    return Reader(
        readerId = id,
        readerName = this.readerName,
        address = this.address,
        libraryCard = libraryCard
    )
}

fun Reader.toResponse(): ResponseReader {
    return ResponseReader(
        readerId = this.readerId,
        readerName = this.readerName,
        address = this.address,
        libraryCardNumber = this.libraryCard.toResponse()
    )
}