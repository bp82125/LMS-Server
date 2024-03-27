package com.ct467.libmansys.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "employees")
class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    var account: Account? = null,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false,
) {

    fun removeAccount() {
        this.account?.employee = null
        this.account = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (birthDate != other.birthDate) return false
        if (phoneNumber != other.phoneNumber) return false
        if (account != other.account) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + fullName.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + (account?.hashCode() ?: 0)
        result = 31 * result + deleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "Employee(id=$id, fullName='$fullName', birthDate=$birthDate, phoneNumber='$phoneNumber', account=$account, deleted=$deleted)"
    }
}