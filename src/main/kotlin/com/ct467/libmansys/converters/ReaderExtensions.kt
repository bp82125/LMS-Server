package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestReader
import com.ct467.libmansys.dtos.ResponseReader
import com.ct467.libmansys.dtos.ResponseReaderCount
import com.ct467.libmansys.models.LibraryCard
import com.ct467.libmansys.models.Reader

fun RequestReader.toEntity(id: Long = 0, libraryCard: LibraryCard? = null): Reader {
    return Reader(
        id = id,
        readerName = this.readerName,
        address = this.address,
        libraryCard = libraryCard
    )
}

fun Reader.toResponse(): ResponseReader {
    return ResponseReader(
        id = this.id,
        readerName = this.readerName,
        address = this.address,
        libraryCard = this.libraryCard?.toResponse(),
        deleted = this.deleted
    )
}

fun Reader.toQuantity(): ResponseReaderCount{
    return ResponseReaderCount(
        id = this.id,
        readerName = this.readerName,
        numberOfBooks = this.libraryCard?.countDetails() ?: 0,
        numberOfCheckouts = this.libraryCard?.countCheckouts() ?: 0
    )
}