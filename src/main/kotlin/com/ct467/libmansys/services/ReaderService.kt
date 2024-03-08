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

    fun findReaderById(readerId: Long): ResponseReader {
        val reader = readerRepository
            .findById(readerId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Reader", id = readerId) }

        return reader.toResponse()
    }

    fun createReader(requestReader: RequestReader): ResponseReader {
        val libraryCard = libraryCardRepository
            .findById(requestReader.libraryCardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "LibraryCard", id = requestReader.libraryCardNumber) }

        val reader = requestReader.toEntity(
            libraryCard = libraryCard
        )

        val createdReader = readerRepository.save(reader)
        return createdReader.toResponse()
    }

    fun updateReader(readerId: Long, requestReader: RequestReader): ResponseReader {
        val libraryCard = libraryCardRepository
            .findById(requestReader.libraryCardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "LibraryCard", id = requestReader.libraryCardNumber) }

        val reader = requestReader.toEntity(
            id = readerId,
            libraryCard = libraryCard
        )

        val updatedReader = readerRepository.save(reader)
        return updatedReader.toResponse()
    }

    fun deleteReader(readerId: Long) {
        if (!readerRepository.existsById(readerId)) {
            throw EntityWithIdNotFoundException(objectName = "Reader", id = readerId)
        }

        return readerRepository.deleteById(readerId)
    }
}