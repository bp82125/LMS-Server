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

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false,

    @OneToMany(
        mappedBy = "author",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = Book::class
    )
    val books: List<Book> = mutableListOf()
) {
    @PreRemove
    fun preRemove() {
        books.forEach { it.author = null }
    }

    fun numberOfBooks(): Int {
        return books.size
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        if (id != other.id) return false
        if (authorName != other.authorName) return false
        if (website != other.website) return false
        if (note != other.note) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + authorName.hashCode()
        result = 31 * result + website.hashCode()
        result = 31 * result + note.hashCode()
        result = 31 * result + deleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "Author(id=$id, authorName='$authorName', website='$website', note='$note', deleted=$deleted)"
    }

}