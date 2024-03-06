package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestAuthor
import com.ct467.libmansys.dtos.ResponseAuthor
import com.ct467.libmansys.models.Author

fun RequestAuthor.toEntity(id: Long = 0): Author {
    return Author(
        authorId = id,
        authorName = this.authorName,
        website = this.website,
        note = this.note
    )
}

fun Author.toResponse(): ResponseAuthor {
    return ResponseAuthor(
        id = this.authorId,
        authorName = this.authorName,
        website = this.website,
        note = this.note
    )
}