package com.ct467.libmansys.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "checkouts")
class Checkout(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_number")
    var libraryCard: LibraryCard?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    var employee: Employee?,

    @Column(name = "checkout_date")
    var checkoutDate: LocalDate,

    @OneToMany(
        mappedBy = "checkout",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = CheckoutDetail::class
    )
    var checkoutDetails: List<CheckoutDetail> = mutableListOf()
) {

    fun returnedAll(): Boolean {
        return checkoutDetails.all { it.returned }
    }

    fun countDetails(): Int {
        return checkoutDetails.size
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Checkout) return false

        if (id != other.id) return false
        if (libraryCard != other.libraryCard) return false
        if (employee != other.employee) return false
        if (checkoutDate != other.checkoutDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + libraryCard.hashCode()
        result = 31 * result + employee.hashCode()
        result = 31 * result + checkoutDate.hashCode()
        return result
    }

    override fun toString(): String {
        return "Checkout(id=$id, libraryCard=$libraryCard, employee=$employee, checkoutDate=$checkoutDate)"
    }

}