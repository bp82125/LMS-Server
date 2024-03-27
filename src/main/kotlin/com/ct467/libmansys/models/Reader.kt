package com.ct467.libmansys.models

import jakarta.persistence.*

@Entity
@Table(name = "readers")
class Reader(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var readerName: String,

    @Column(name = "address", nullable = false)
    var address: String = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "card_number", referencedColumnName = "cardNumber")
    var libraryCard: LibraryCard? = null,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false
) {

    fun removeLibraryCard() {
        this.libraryCard?.reader = null
        this.libraryCard = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Reader) return false

        if (id != other.id) return false
        if (readerName != other.readerName) return false
        if (address != other.address) return false
        if (libraryCard != other.libraryCard) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + readerName.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + (libraryCard?.hashCode() ?: 0)
        result = 31 * result + deleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "Reader(id=$id, readerName='$readerName', address='$address', libraryCard=$libraryCard, deleted=$deleted)"
    }
}