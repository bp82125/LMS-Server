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
@RequestMapping("\${api.endpoint.base-url}/library-cards")
class LibraryCardController(
    @Autowired private val libraryCardService: LibraryCardService
) {
    @GetMapping("", "/")
    fun findAllLibraryCards(): ResponseEntity<ApiResponse<List<ResponseLibraryCard>>> {
        val libraryCards = libraryCardService.findAllLibraryCards()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = libraryCards,
                message = "Found library cards"
            )
        )
    }

    @GetMapping("/{cardNumber}")
    fun findLibraryCardByNumber(@PathVariable cardNumber: Long): ResponseEntity<ApiResponse<ResponseLibraryCard>> {
        val libraryCard = libraryCardService.findLibraryCardByNumber(cardNumber)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = libraryCard,
                message = "Found library card with number: $cardNumber"
            )
        )
    }

    @PostMapping("")
    fun createLibraryCard(@Valid @RequestBody requestLibraryCard: RequestLibraryCard): ResponseEntity<ApiResponse<ResponseLibraryCard>> {
        val createdLibraryCard = libraryCardService.createLibraryCard(requestLibraryCard)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdLibraryCard,
                message = "Library card created successfully"
            )
        )
    }

    @PutMapping("/{cardNumber}")
    fun updateLibraryCard(
        @PathVariable cardNumber: Long,
        @Valid @RequestBody requestLibraryCard: RequestLibraryCard
    ): ResponseEntity<ApiResponse<ResponseLibraryCard>> {
        val updatedLibraryCard = libraryCardService.updateLibraryCard(cardNumber, requestLibraryCard)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedLibraryCard,
                message = "Library card updated successfully"
            )
        )
    }

    @DeleteMapping("/{cardNumber}")
    fun deleteLibraryCard(@PathVariable cardNumber: Long): ResponseEntity<ApiResponse<Void>> {
        libraryCardService.deleteLibraryCard(cardNumber)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Library card deleted successfully"
            )
        )
    }
}