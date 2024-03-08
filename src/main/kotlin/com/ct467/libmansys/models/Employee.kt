package com.ct467.libmansys.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "employees")
class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val employeeId: Long = 1,

    @Column(name = "full_name", nullable = false)
    val fullName: String,

    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDate,

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        if (employeeId != other.employeeId) return false
        if (fullName != other.fullName) return false
        if (birthDate != other.birthDate) return false
        if (phoneNumber != other.phoneNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = employeeId.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        return result
    }

    override fun toString(): String {
        return "Employee(employeeId=$employeeId, fullName='$fullName', birthDate=$birthDate, phoneNumber='$phoneNumber')"
    }
}