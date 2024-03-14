package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestReader
import com.ct467.libmansys.dtos.ResponseReader
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.LibraryCardRepository
import com.ct467.libmansys.repositories.ReaderRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class ReaderService(
    @Autowired private val readerRepository: ReaderRepository,
    @Autowired private val libraryCardRepository: LibraryCardRepository
) {
    fun findAllReaders(): List<ResponseReader> {
        return readerRepository
            .findAll()
            .map { reader -> reader.toResponse() }
    }

    fun findReaderById(id: Long): ResponseReader {
        val reader = readerRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = "$id") }

        return reader.toResponse()
    }

    fun createReader(requestReader: RequestReader): ResponseReader {
        val reader = requestReader.toEntity()
        val createdReader = readerRepository.save(reader)
        return createdReader.toResponse()
    }

    fun updateReader(id: Long, requestReader: RequestReader): ResponseReader {
        val libraryCard = requestReader.libraryCardNumber?.let {
            libraryCardRepository
                .findById(it)
                .orElseThrow { EntityWithIdNotFoundException(objectName = "LibraryCard", id = "$requestReader.libraryCardNumber") }
        }

        val reader = requestReader.toEntity(
            id = id,
            libraryCard = libraryCard
        )

        val updatedReader = readerRepository.save(reader)
        return updatedReader.toResponse()
    }

    fun deleteReader(id: Long) {
        val reader = readerRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = "$id") }

        reader?.libraryCard?.let {
            libraryCardRepository.deleteById(it.cardNumber)
        }

        reader.removeLibraryCard()
        return readerRepository.deleteById(id)
    }
}