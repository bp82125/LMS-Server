package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestAuthor
import com.ct467.libmansys.dtos.ResponseAuthor
import com.ct467.libmansys.dtos.ResponseAuthorTotal
import com.ct467.libmansys.services.AuthorService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/authors")
class AuthorController(
    @Autowired private val authorService: AuthorService
) {
    @GetMapping("/totals", "/totals/")
    fun countAllAuthors(): ResponseEntity<ApiResponse<List<ResponseAuthorTotal>>> {
        val totals = authorService.countTotal()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = totals,
                message = "Found total count of authors"
            )
        )
    }

    @GetMapping("", "/")
    fun findAllAuthors(
        @RequestParam(required = false, defaultValue = "available") status: String?,
    ): ResponseEntity<ApiResponse<List<ResponseAuthor>>> {
        val authors = authorService.findAllAuthors(status)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = authors,
                message = "Found authors"
            )
        )
    }

    @GetMapping("/{id}", "/{id}/")
    fun findAuthorById(@PathVariable id: Long): ResponseEntity<ApiResponse<ResponseAuthor>> {
        val author = authorService.findAuthorById(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = author,
                message = "Found author with id: $id"
            )
        )
    }

    @PostMapping("", "/")
    fun createAuthor(@Valid @RequestBody requestAuthor: RequestAuthor): ResponseEntity<ApiResponse<ResponseAuthor>> {
        val createdAuthor = authorService.createAuthor(requestAuthor)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdAuthor,
                message = "Author created successfully"
            )
        )
    }

    @PutMapping("/{id}", "/{id}/")
    fun updateAuthor(
        @PathVariable id: Long,
        @Valid @RequestBody requestAuthor: RequestAuthor
    ): ResponseEntity<ApiResponse<ResponseAuthor>> {
        val updatedAuthor = authorService.updateAuthor(id, requestAuthor)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedAuthor,
                message = "Author updated successfully"
            )
        )
    }

    @DeleteMapping("/{id}", "/{id}/")
    fun deleteAuthor(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> {
        authorService.deleteAuthor(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Author deleted successfully"
            )
        )
    }
}