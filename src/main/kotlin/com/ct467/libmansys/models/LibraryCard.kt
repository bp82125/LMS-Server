package com.ct467.libmansys.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "library_cards")
class LibraryCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cardNumber: Long = 1,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "expiration_date", nullable = false)
    val expirationDate: LocalDate,

    @Column(name = "note", nullable = false)
    val note: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibraryCard) return false

        if (cardNumber != other.cardNumber) return false
        if (startDate != other.startDate) return false
        if (expirationDate != other.expirationDate) return false
        if (note != other.note) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cardNumber.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + expirationDate.hashCode()
        result = 31 * result + note.hashCode()
        return result
    }

    override fun toString(): String {
        return "LibraryCard(cardNumber=$cardNumber, startDate=$startDate, expirationDate=$expirationDate, note='$note')"
    }
}