package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestAuthor
import com.ct467.libmansys.dtos.ResponseAuthor
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.AuthorRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthorService(
    @Autowired private val authorRepository: AuthorRepository
) {
    fun findAllAuthors(status: String? = null): List<ResponseAuthor> {
        return when (status) {
            "available" -> authorRepository.findAllByDeletedFalse().map { it.toResponse() }
            "deleted" -> authorRepository.findAllByDeletedTrue().map { it.toResponse() }
            "all" -> authorRepository.findAll().map { it.toResponse() }
            else -> authorRepository.findAllByDeletedFalse().map { it.toResponse() }
        }
    }

    fun findAuthorById(id: Long): ResponseAuthor {
        val author = authorRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }
            .also { if(it.deleted) throw EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }

        return author.toResponse()
    }

    fun createAuthor(requestAuthor: RequestAuthor): ResponseAuthor {
        val author = requestAuthor.toEntity()
        val createdAuthor = authorRepository.save(author)
        return createdAuthor.toResponse()
    }

    fun updateAuthor(id: Long, requestAuthor: RequestAuthor): ResponseAuthor {
        authorRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }
            .also { if(it.deleted) throw EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }

        val author = requestAuthor.toEntity(id)
        val updatedAuthor = authorRepository.save(author)
        return updatedAuthor.toResponse()
    }

    fun deleteAuthor(id: Long){
        val author = authorRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }
            .also { if(it.deleted) throw EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }

        authorRepository.save(
            author.apply { this.deleted = true }
        )
    }
}