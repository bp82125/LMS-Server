package com.ct467.libmansys.repositories

import com.ct467.libmansys.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUsername(username: String): Optional<Account>

    fun existsByUsername(username: String): Boolean
}