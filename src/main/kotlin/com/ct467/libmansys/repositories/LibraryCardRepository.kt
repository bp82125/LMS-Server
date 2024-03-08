package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.LibraryCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LibraryCardRepository : JpaRepository<LibraryCard, Long>