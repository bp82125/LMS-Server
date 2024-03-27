package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Book
import com.ct467.libmansys.models.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findAllByDeletedFalse(): List<Employee>
    fun findAllByDeletedTrue(): List<Employee>
}
