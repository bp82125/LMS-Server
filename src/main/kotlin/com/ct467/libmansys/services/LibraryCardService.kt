package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.LibraryCardRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class LibraryCardService(
    @Autowired private val libraryCardRepository: LibraryCardRepository
) {
    fun findAllLibraryCards(): List<ResponseLibraryCard> {
        return libraryCardRepository
            .findAll()
            .map { libraryCard -> libraryCard.toResponse() }
    }

    fun findLibraryCardByNumber(cardNumber: Long): ResponseLibraryCard {
        val libraryCard = libraryCardRepository
            .findById(cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "LibraryCard", id = cardNumber) }

        return libraryCard.toResponse()
    }

    fun createLibraryCard(requestLibraryCard: RequestLibraryCard): ResponseLibraryCard {
        val libraryCard = requestLibraryCard.toEntity()
        val createdLibraryCard = libraryCardRepository.save(libraryCard)
        return createdLibraryCard.toResponse()
    }

    fun updateLibraryCard(cardNumber: Long, requestLibraryCard: RequestLibraryCard): ResponseLibraryCard {
        if (!libraryCardRepository.existsById(cardNumber)) {
            throw EntityWithIdNotFoundException("LibraryCard", cardNumber)
        }

        val libraryCard = requestLibraryCard.toEntity(cardNumber)
        val updatedLibraryCard = libraryCardRepository.save(libraryCard)
        return updatedLibraryCard.toResponse()
    }

    fun deleteLibraryCard(cardNumber: Long) {
        if (!libraryCardRepository.existsById(cardNumber)) {
            throw EntityWithIdNotFoundException("LibraryCard", cardNumber)
        }

        return libraryCardRepository.deleteById(cardNumber)
    }
}