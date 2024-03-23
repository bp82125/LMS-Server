package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestLibraryCard
import com.ct467.libmansys.dtos.ResponseLibraryCard
import com.ct467.libmansys.services.LibraryCardService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/readers")
class LibraryCardController(
    @Autowired private val libraryCardService: LibraryCardService
) {
    @GetMapping("/library-cards", "/library-cards/")
    fun findAllLibraryCards(
        @RequestParam(required = false, defaultValue = "available") status: String?
    ): ResponseEntity<ApiResponse<List<ResponseLibraryCard>>> {
        val libraryCards = libraryCardService.findAllLibraryCards(status)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = libraryCards,
                message = "Found library cards"
            )
        )
    }

    @GetMapping("/{readerId}/library-cards", "/{readerId}/library-cards/")
    fun findLibraryCardByReaderId(
        @PathVariable readerId: Long
    ): ResponseEntity<ApiResponse<ResponseLibraryCard>> {
        val libraryCard = libraryCardService.findLibraryCardOfReader(readerId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = libraryCard,
                message = "Found library card with of reader: $readerId"
            )
        )
    }

    @PostMapping("/{readerId}/library-cards", "/{readerId}/library-cards/")
    fun createLibraryCard(
        @PathVariable readerId: Long,
        @Valid @RequestBody requestLibraryCard: RequestLibraryCard
    ): ResponseEntity<ApiResponse<ResponseLibraryCard>> {
        val createdLibraryCard = libraryCardService.createLibraryCard(readerId, requestLibraryCard)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdLibraryCard,
                message = "Library card created successfully"
            )
        )
    }

    @PutMapping("/{readerId}/library-cards", "/{readerId}/library-cards/")
    fun updateLibraryCard(
        @PathVariable readerId: Long,
        @Valid @RequestBody requestLibraryCard: RequestLibraryCard
    ): ResponseEntity<ApiResponse<ResponseLibraryCard>> {
        val updatedLibraryCard = libraryCardService.updateLibraryCard(readerId, requestLibraryCard)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedLibraryCard,
                message = "Library card updated successfully"
            )
        )
    }

    @DeleteMapping("/{readerId}/library-cards", "/{readerId}/library-cards/")
    fun deleteLibraryCard(@PathVariable readerId: Long): ResponseEntity<ApiResponse<Void>> {
        libraryCardService.deleteLibraryCard(readerId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Library card deleted successfully"
            )
        )
    }
}