package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestAuthor
import com.ct467.libmansys.dtos.RequestBook
import com.ct467.libmansys.dtos.ResponseAuthor
import com.ct467.libmansys.dtos.ResponseBook
import com.ct467.libmansys.services.BookService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/books")
class BookController(
    @Autowired private val bookService: BookService
) {
    @GetMapping("/totals", "/totals/")
    fun countAllBooks(): ResponseEntity<ApiResponse<List<Any>>> {
        val totals = bookService.countTotal()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = totals,
                message = "Found total count of books"
            )
        )
    }

    @GetMapping("", "/")
    fun findAllBooks(
        @RequestParam(required = false, defaultValue = "available") status: String?,
    ): ResponseEntity<ApiResponse<List<ResponseBook>>> {
        val books = bookService.findAllBooks(status)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = books,
                message = "Found books"
            )
        )
    }

    @GetMapping("/{id}", "/{id}/")
    fun findBookById(@PathVariable id: Long): ResponseEntity<ApiResponse<ResponseBook>> {
        val book = bookService.findBookById(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = book,
                message = "Found book with id: $id"
            )
        )
    }

    @PostMapping("", "/")
    fun createBook(@Valid @RequestBody requestBook: RequestBook): ResponseEntity<ApiResponse<ResponseBook>> {
        val createdBook = bookService.createBook(requestBook)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdBook,
                message = "Book created successfully"
            )
        )
    }

    @PutMapping("/{id}", "/{id}/")
    fun updateBook(
        @PathVariable id: Long,
        @Valid @RequestBody requestBook: RequestBook
    ): ResponseEntity<ApiResponse<RequestBook>> {
        val updatedBook = bookService.updateBook(id, requestBook)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = requestBook,
                message = "Book updated successfully"
            )
        )
    }

    @DeleteMapping("/{id}", "/{id}/")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> {
        bookService.deleteBook(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Book deleted successfully"
            )
        )
    }
}