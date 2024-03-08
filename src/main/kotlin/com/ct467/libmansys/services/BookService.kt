package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestBook
import com.ct467.libmansys.dtos.ResponseBook
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.AuthorRepository
import com.ct467.libmansys.repositories.BookRepository
import com.ct467.libmansys.repositories.CategoryRepository
import com.ct467.libmansys.repositories.PublisherRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class BookService(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val authorRepository: AuthorRepository,
    @Autowired private val publisherRepository: PublisherRepository,
    @Autowired private val categoryRepository: CategoryRepository
) {
    fun findAllBooks(): List<ResponseBook> {
        return bookRepository.findAll().map { it.toResponse() }
    }

    fun findBookById(id: Long): ResponseBook{
        return bookRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Book", id = id) }
    }

    fun createBook(requestBook: RequestBook): ResponseBook {
        val author = authorRepository
            .findById(requestBook.authorId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = requestBook.authorId) }

        val publisher = publisherRepository
            .findById(requestBook.publisherId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = requestBook.publisherId) }

        val category = categoryRepository
            .findById(requestBook.categoryId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = requestBook.categoryId) }

        val book = requestBook.toEntity(
            author = author,
            publisher = publisher,
            category = category
        )

        val createdBook = bookRepository.save(book)
        return createdBook.toResponse()
    }

    fun updateBook(id: Long, requestBook: RequestBook): ResponseBook {
        val author = authorRepository
            .findById(requestBook.authorId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = id) }

        val publisher = publisherRepository
            .findById(requestBook.publisherId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = id) }

        val category = categoryRepository
            .findById(requestBook.categoryId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = id) }

        val book = requestBook.toEntity(
            id = id,
            author = author,
            publisher = publisher,
            category = category
        )

        val updatedBook = bookRepository.save(book)
        return updatedBook.toResponse()
    }

    fun deleteBook(id: Long){
        if(!bookRepository.existsById(id)){
            throw EntityWithIdNotFoundException(objectName =  "Book", id = id)
        }

        bookRepository.deleteById(id)
    }
}