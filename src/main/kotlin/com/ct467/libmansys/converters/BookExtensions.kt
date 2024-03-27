package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestBook
import com.ct467.libmansys.dtos.ResponseBook
import com.ct467.libmansys.models.Author
import com.ct467.libmansys.models.Book
import com.ct467.libmansys.models.Category
import com.ct467.libmansys.models.Publisher

fun RequestBook.toEntity(id: Long = 0, author: Author, category: Category, publisher: Publisher): Book {
    return Book(
        id = id,
        bookName = this.name,
        publicationYear = this.publicationYear,
        author = author,
        category = category,
        publisher = publisher
    )
}

fun Book.toResponse(): ResponseBook {
    return ResponseBook(
        id = this.id,
        bookName = this.bookName,
        publicationYear = this.publicationYear,
        author = this.author?.toResponse(),
        category = this.category?.toResponse(),
        publisher = this.publisher?.toResponse(),
        deleted = this.deleted
    )
}