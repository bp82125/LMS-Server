package com.ct467.libmansys.models
import jakarta.persistence.*

@Entity
@Table(name = "publishers")
class Publisher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var publisherName: String,

    @Column(name = "address", nullable = false)
    var address: String = "",

    @Column(name = "email", nullable = false)
    var email: String = "",

    @Column(name = "representative_info", nullable = false)
    var representativeInfo: String = "",

    @OneToMany(
        mappedBy = "publisher",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = Book::class
    )
    val books: List<Book> = mutableListOf()
) {
    @PreRemove
    fun preRemove(){
        books.forEach { it.publisher = null }
    }

    fun numberOfBooks(): Int {
        return books.size
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Publisher) return false

        if (id != other.id) return false
        if (publisherName != other.publisherName) return false
        if (address != other.address) return false
        if (email != other.email) return false
        if (representativeInfo != other.representativeInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + publisherName.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + representativeInfo.hashCode()
        return result
    }

    override fun toString(): String {
        return "Publisher(publisherId=$id, publisherName='$publisherName', address='$address', email='$email', representativeInfo='$representativeInfo')"
    }
}