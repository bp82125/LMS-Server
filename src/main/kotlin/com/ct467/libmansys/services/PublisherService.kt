package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestPublisher
import com.ct467.libmansys.dtos.ResponsePublisher
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.PublisherRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class PublisherService(
    @Autowired private val publisherRepository: PublisherRepository
) {
    fun findAllPublishers(status: String? = null): List<ResponsePublisher> {
        return when (status) {
            "available" -> publisherRepository.findAllByDeletedFalse().map { it.toResponse() }
            "deleted" -> publisherRepository.findAllByDeletedTrue().map { it.toResponse() }
            "all" -> publisherRepository.findAll().map { it.toResponse() }
            else -> publisherRepository.findAll().map { it.toResponse() }
        }
    }

    fun findPublisherById(id: Long): ResponsePublisher {
        val publisher = publisherRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }

        return publisher.toResponse()
    }

    fun createPublisher(requestPublisher: RequestPublisher): ResponsePublisher {
        val publisher = requestPublisher.toEntity()
        val createdPublisher = publisherRepository.save(publisher)
        return createdPublisher.toResponse()
    }

    fun updatePublisher(id: Long, requestPublisher: RequestPublisher): ResponsePublisher {
        val oldPublisher = publisherRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }

        val publisher = requestPublisher.toEntity(id)
        val updatedPublisher = publisherRepository.save(publisher)
        return updatedPublisher.toResponse()
    }

    fun deletePublisher(id: Long){
        val publisher = publisherRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }

        publisherRepository.save(
            publisher.apply { this.deleted = true }
        )
    }
}