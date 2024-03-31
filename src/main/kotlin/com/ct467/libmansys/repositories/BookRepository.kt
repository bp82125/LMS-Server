package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Book
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findAllByDeletedFalse(): List<Book>
    fun findAllByDeletedTrue(): List<Book>
}