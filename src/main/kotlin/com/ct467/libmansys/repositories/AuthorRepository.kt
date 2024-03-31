package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Author
import com.ct467.libmansys.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findAllByDeletedFalse(): List<Author>
    fun findAllByDeletedTrue(): List<Author>
}