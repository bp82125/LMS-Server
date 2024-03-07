package com.ct467.libmansys.models
import jakarta.persistence.*


@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryId: Long = 1,

    @Column(name = "name", nullable = false)
    val categoryName: String,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Category) return false

        if (categoryId != other.categoryId) return false
        if (categoryName != other.categoryName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = categoryId.hashCode()
        result = 31 * result + categoryName.hashCode()
        return result
    }

    override fun toString(): String {
        return "Category(categoryId=$categoryId, categoryName='$categoryName')"
    }
}