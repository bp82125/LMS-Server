package com.ct467.libmansys.models
import jakarta.persistence.*


@Entity
@Table(name = "authors")
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val authorId: Long = 1,

    @Column(name = "name", nullable = false)
    val authorName: String,

    @Column(name = "website", nullable = false)
    val website: String = "",

    @Column(name = "note", nullable = false)
    val note: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        if (authorId != other.authorId) return false
        if (authorName != other.authorName) return false
        if (website != other.website) return false
        if (note != other.note) return false

        return true
    }

    override fun hashCode(): Int {
        var result = authorId.hashCode()
        result = 31 * result + authorName.hashCode()
        result = 31 * result + website.hashCode()
        result = 31 * result + note.hashCode()
        return result
    }

    override fun toString(): String {
        return "Author(authorId=$authorId, authorName='$authorName', website='$website', note='$note')"
    }
}