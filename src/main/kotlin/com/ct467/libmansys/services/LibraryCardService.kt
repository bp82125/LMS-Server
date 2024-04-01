package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toQuantity
import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCardTotal
import com.ct467.libmansys.exceptions.AssociatedEntityNotFoundException
import com.ct467.libmansys.exceptions.EntityAlreadyAssociatedException
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
    @Autowired private val readerRepository: ReaderRepository,
) {
    fun countTotal(): ResponseLibraryCardTotal {
        val libraryCards = libraryCardRepository.findAll()
        return ResponseLibraryCardTotal(
            libraryCards = libraryCards.map { it.toQuantity() },
            numberOfLibraryCards = libraryCards.size
        )
    }

    fun findAllLibraryCards(status: String? = null): List<ResponseLibraryCard> {
        return when (status) {
            "deleted" -> libraryCardRepository.findAllByDeletedTrue().map { it.toResponse() }
            "available" -> libraryCardRepository.findAllByDeletedFalse().map { it.toResponse() }
            "all" -> libraryCardRepository.findAll().map { it.toResponse() }
            else -> libraryCardRepository.findAll().map { it.toResponse() }
        }
    }

    fun findLibraryCardOfReader(readerId: Long): ResponseLibraryCard? {
        val reader = readerRepository.findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = "$readerId") }

        val cardNumber = reader.libraryCard?.cardNumber
            ?: throw AssociatedEntityNotFoundException("Library card", "Reader", readerId.toString())

        val libraryCard = libraryCardRepository.findById(cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Library card", id = "$readerId") }

        return libraryCard.toResponse()
    }

    fun createLibraryCard(readerId: Long, requestLibraryCard: RequestLibraryCard): ResponseLibraryCard {
        val reader = readerRepository
            .findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Reader", id = "$readerId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Reader", id = "$readerId") }

        if (reader.libraryCard != null){
            throw EntityAlreadyAssociatedException(
                entityName = "Reader",
                entityId = "$readerId",
                associatedEntityName = "Library card",
                associatedEntityId = "${reader.libraryCard!!.cardNumber}"
            )
        }

        val libraryCard = requestLibraryCard.toEntity(
            reader = reader,
            startDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusMonths(requestLibraryCard.cardDuration)
        )

        val createdLibraryCard = libraryCardRepository.save(libraryCard)

        reader
            .apply { this.libraryCard = createdLibraryCard }
            .let { readerRepository.save(it) }

        return createdLibraryCard.toResponse()
    }

    fun updateLibraryCard(readerId: Long, requestLibraryCard: RequestLibraryCard): ResponseLibraryCard {
        val reader = readerRepository.findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = "$readerId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Reader", id = "$readerId") }

        val oldLibraryCard = reader.libraryCard
            ?: throw AssociatedEntityNotFoundException("Library card", "Reader", readerId.toString())

        val newLibraryCard = oldLibraryCard.apply {
            this.expirationDate = this.expirationDate.plusMonths(requestLibraryCard.cardDuration)
            this.note = requestLibraryCard.note
        }

        val updatedLibraryCard = libraryCardRepository.save(newLibraryCard)
        return updatedLibraryCard.toResponse()
    }

    fun deleteLibraryCard(readerId: Long) {
        val reader = readerRepository.findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = "$readerId") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Reader", id = "$readerId") }

        val libraryCard = reader.libraryCard
            ?: throw AssociatedEntityNotFoundException(entityName = "Reader", id = "$readerId", association = "Library card")

        libraryCard.apply { this.deleted = true }
        libraryCardRepository.save(libraryCard)
    }
}