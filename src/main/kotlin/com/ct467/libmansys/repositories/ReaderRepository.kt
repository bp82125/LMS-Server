package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Book
import com.ct467.libmansys.models.Reader
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReaderRepository : JpaRepository<Reader, Long> {
    fun findAllByDeletedFalse(): List<Reader>
    fun findAllByDeletedTrue(): List<Reader>
}