package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Publisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublisherRepository : JpaRepository<Publisher, Long>