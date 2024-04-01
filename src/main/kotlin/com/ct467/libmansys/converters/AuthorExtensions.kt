package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestAuthor
import com.ct467.libmansys.dtos.ResponseAuthor
import com.ct467.libmansys.dtos.ResponseAuthorQuantity
import com.ct467.libmansys.models.Author

fun RequestAuthor.toEntity(id: Long = 0): Author {
    return Author(
        id = id,
        authorName = this.authorName,
        website = this.website,
        note = this.note
    )
}

fun Author.toResponse(): ResponseAuthor {
    return ResponseAuthor(
        id = this.id,
        authorName = this.authorName,
        website = this.website,
        note = this.note,
        numberOfBooks = this.numberOfBooks()
    )
}

fun Author.toQuantity(): ResponseAuthorQuantity {
    return ResponseAuthorQuantity(
        id = this.id,
        authorName = this.authorName,
        numberOfBooks = numberOfBooks()
    )
}