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
    var libraryCard: LibraryCard? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Reader) return false

        if (id != other.id) return false
        if (readerName != other.readerName) return false
        if (address != other.address) return false
        if (libraryCard != other.libraryCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + readerName.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + libraryCard.hashCode()
        return result
    }

    override fun toString(): String {
        return "Reader(readerId=${id}d, readerName='$readerName', address='$address', cardNumber='$libraryCard')"
    }

    fun removeLibraryCard() {
        this.libraryCard?.reader = null
        this.libraryCard = null
    }
}