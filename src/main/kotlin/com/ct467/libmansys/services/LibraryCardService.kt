package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.exceptions.AssociatedEntityNotFoundException
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.LibraryCardRepository
import com.ct467.libmansys.repositories.ReaderRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class LibraryCardService(
    @Autowired private val libraryCardRepository: LibraryCardRepository,
    @Autowired private val readerRepository: ReaderRepository
) {
    fun findAllLibraryCards(): List<ResponseLibraryCard> {
        return libraryCardRepository
            .findAll()
            .map { libraryCard -> libraryCard.toResponse() }
    }

    fun findLibraryCardOfReader(readerId: Long): ResponseLibraryCard? {
        val reader = readerRepository.findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = readerId) }

        val cardNumber = reader.libraryCard?.cardNumber
            ?: throw AssociatedEntityNotFoundException("Library card", "Reader", readerId)

        val libraryCard = libraryCardRepository.findById(cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Library card", id = cardNumber) }

        return libraryCard.toResponse()
    }

    fun createLibraryCard(readerId: Long, requestLibraryCard: RequestLibraryCard): ResponseLibraryCard {
        val reader = readerRepository
            .findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Reader", id = readerId) }

        val libraryCard = requestLibraryCard.toEntity(
            reader = reader,
            startDate = LocalDate.now()
        )

        val createdLibraryCard = libraryCardRepository.save(libraryCard)

        reader
            .apply { this.libraryCard = createdLibraryCard }
            .let { readerRepository.save(it) }

        return createdLibraryCard.toResponse()
    }

    fun updateLibraryCard(readerId: Long, requestLibraryCard: RequestLibraryCard): ResponseLibraryCard {
        val reader = readerRepository.findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = readerId) }

        val cardNumber = reader.libraryCard?.cardNumber
            ?: throw AssociatedEntityNotFoundException("Library card", "Reader", readerId)

        val oldLibraryCard = libraryCardRepository
            .findById(cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Library card", id = cardNumber) }

        val newLibraryCard = requestLibraryCard.toEntity(
            cardNumber = cardNumber,
            reader = oldLibraryCard.reader,
            startDate = oldLibraryCard.startDate
        )

        val updatedLibraryCard = libraryCardRepository.save(newLibraryCard)
        return updatedLibraryCard.toResponse()
    }

    fun deleteLibraryCard(readerId: Long) {
        val reader = readerRepository.findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = readerId) }

        val cardNumber = reader.libraryCard?.cardNumber
            ?: throw AssociatedEntityNotFoundException("Library card", "Reader", readerId)

        val libraryCard = libraryCardRepository.findById(cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Library card", id = cardNumber) }

        reader.removeLibraryCard()
        readerRepository.save(reader)

        return libraryCardRepository.deleteById(cardNumber)
    }
}