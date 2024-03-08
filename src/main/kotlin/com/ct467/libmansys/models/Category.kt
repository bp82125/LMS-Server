package com.ct467.libmansys.models
import jakarta.persistence.*


@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 1,

    @Column(name = "name", nullable = false)
    val categoryName: String,

    @OneToMany(
        mappedBy = "category",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = Book::class
    )
    val books: List<Book> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Category) return false

        if (id != other.id) return false
        if (categoryName != other.categoryName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + categoryName.hashCode()
        return result
    }

    override fun toString(): String {
        return "Category(categoryId=$id, categoryName='$categoryName')"
    }

    fun numberOfBooks(): Int {
        return books.size
    }
}