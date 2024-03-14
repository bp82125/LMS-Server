package com.ct467.libmansys.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "library_cards")
class LibraryCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cardNumber: Long = 1,

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDate,

    @Column(name = "expiration_date", nullable = false)
    var expirationDate: LocalDate,

    @Column(name = "note")
    var note: String = "",

    @OneToOne(
        mappedBy = "libraryCard",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = Reader::class
    )
    var reader: Reader? = null,

    @OneToMany(
        mappedBy = "libraryCard",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = Checkout::class
    )
    var checkouts: List<Checkout> = mutableListOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibraryCard) return false

        if (cardNumber != other.cardNumber) return false
        if (startDate != other.startDate) return false
        if (expirationDate != other.expirationDate) return false
        if (note != other.note) return false
        if (reader != other.reader) return false
        if (checkouts != other.checkouts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cardNumber.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + expirationDate.hashCode()
        result = 31 * result + note.hashCode()
        result = 31 * result + (reader?.hashCode() ?: 0)
        result = 31 * result + checkouts.hashCode()
        return result
    }

    override fun toString(): String {
        return "LibraryCard(cardNumber=$cardNumber, startDate=$startDate, expirationDate=$expirationDate, note='$note', reader=$reader, checkouts=$checkouts)"
    }
}