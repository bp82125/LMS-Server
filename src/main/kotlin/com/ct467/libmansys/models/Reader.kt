package com.ct467.libmansys.models

import jakarta.persistence.*

@Entity
@Table(name = "readers")
class Reader(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val readerId: Long = 1,

    @Column(name = "name", nullable = false)
    val readerName: String,

    @Column(name = "address", nullable = false)
    val address: String = "",

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val libraryCard: LibraryCard
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Reader) return false

        if (readerId != other.readerId) return false
        if (readerName != other.readerName) return false
        if (address != other.address) return false
        if (libraryCard != other.libraryCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = readerId.hashCode()
        result = 31 * result + readerName.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + libraryCard.hashCode()
        return result
    }

    override fun toString(): String {
        return "Reader(readerId=${readerId}d, readerName='$readerName', address='$address', cardNumber='$libraryCard')"
    }
}