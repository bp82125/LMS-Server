package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Checkout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CheckoutRepository : JpaRepository<Checkout, Long>