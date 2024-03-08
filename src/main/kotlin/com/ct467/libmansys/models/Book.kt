package com.ct467.libmansys.models

import jakarta.persistence.*

@Entity
@Table(name = "books")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 1,

    @Column(name = "name", nullable = false)
    val bookName: String,

    @Column(name = "year", nullable = false)
    val publicationYear: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    val author: Author,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    val publisher: Publisher
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false

        if (id != other.id) return false
        if (bookName != other.bookName) return false
        if (publicationYear != other.publicationYear) return false
        if (category != other.category) return false
        if (author != other.author) return false
        if (publisher != other.publisher) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bookName.hashCode()
        result = 31 * result + publicationYear
        result = 31 * result + category.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + publisher.hashCode()
        return result
    }

    override fun toString(): String {
        return "Book(bookId=$id, bookName='$bookName', publicationYear=$publicationYear, category=$category, author=$author, publisher=$publisher)"
    }
}