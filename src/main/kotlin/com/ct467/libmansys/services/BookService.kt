package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toQuantity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestBook
import com.ct467.libmansys.dtos.ResponseBook
import com.ct467.libmansys.dtos.ResponseBookQuantity
import com.ct467.libmansys.dtos.ResponseBookTotal
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
@Transactional
class BookService(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val authorRepository: AuthorRepository,
    @Autowired private val publisherRepository: PublisherRepository,
    @Autowired private val categoryRepository: CategoryRepository,
    @Autowired private val checkoutDetailRepository: CheckoutDetailRepository
) {
    fun countTotal(): ResponseBookTotal {
        val booksWithQuantity = bookRepository.findAll()
            .mapNotNull { book ->
                book.id?.let { id ->
                    val quantity = checkoutDetailRepository.countByBook_Id(id)
                    book.toQuantity(quantity)
                }
            }

        val numberOfBooks = booksWithQuantity.size

        return ResponseBookTotal(
            books = booksWithQuantity,
            numberOfBooks = numberOfBooks
        )
    }

    fun findAllBooks(status: String? = null): List<ResponseBook> {
        return when (status) {
            "available" -> bookRepository.findAllByDeletedFalse().map { it.toResponse() }
            "deleted" -> bookRepository.findAllByDeletedTrue().map { it.toResponse() }
            "all" -> bookRepository.findAll().map { it.toResponse() }
            else -> bookRepository.findAllByDeletedFalse().map { it.toResponse() }
        }
    }


    fun findBookById(id: Long): ResponseBook{
        return bookRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Book", id = "$id") }
    }

    fun createBook(requestBook: RequestBook): ResponseBook {
        val author = authorRepository
            .findById(requestBook.authorId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = "$requestBook.authorId") }

        val publisher = publisherRepository
            .findById(requestBook.publisherId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = "$requestBook.publisherId") }

        val category = categoryRepository
            .findById(requestBook.categoryId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = "$requestBook.categoryId") }

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
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Author", id = "$id") }

        val publisher = publisherRepository
            .findById(requestBook.publisherId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Publisher", id = "$id") }

        val category = categoryRepository
            .findById(requestBook.categoryId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }

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
        val book = bookRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Book", id = "$id") }

        book.apply { this.deleted = true }
        bookRepository.save(book)
    }
}