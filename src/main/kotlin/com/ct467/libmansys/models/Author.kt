package com.ct467.libmansys.models
import jakarta.persistence.*


@Entity
@Table(name = "authors")
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var authorName: String,

    @Column(name = "website", nullable = false)
    var website: String = "",

    @Column(name = "note", nullable = false)
    var note: String = "",

    @OneToMany(
        mappedBy = "author",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = Book::class
    )
    val books: List<Book> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        if (id != other.id) return false
        if (authorName != other.authorName) return false
        if (website != other.website) return false
        if (note != other.note) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + authorName.hashCode()
        result = 31 * result + website.hashCode()
        result = 31 * result + note.hashCode()
        return result
    }

    override fun toString(): String {
        return "Author(authorId=$id, authorName='$authorName', website='$website', note='$note')"
    }

    fun numberOfBooks(): Int {
        return books.size
    }
}