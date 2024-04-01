package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestReader
import com.ct467.libmansys.dtos.ResponseReader
import com.ct467.libmansys.services.ReaderService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/readers")
class ReaderController(
    @Autowired private val readerService: ReaderService
) {
    @GetMapping("/totals", "/totals/")
    fun countAllReaders(): ResponseEntity<ApiResponse<List<Any>>> {
        val totals = readerService.countTotal()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = totals,
                message = "Found total count of readers"
            )
        )
    }

    @GetMapping("", "/")
    fun findAllReaders(
        @RequestParam(required = false, defaultValue = "available") status: String?
    ): ResponseEntity<ApiResponse<List<ResponseReader>>> {
        val readers = readerService.findAllReaders(status)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = readers,
                message = "Found readers"
            )
        )
    }

    @GetMapping("/{readerId}", "/{readerId}/")
    fun findReaderById(@PathVariable readerId: Long): ResponseEntity<ApiResponse<ResponseReader>> {
        val reader = readerService.findReaderById(readerId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = reader,
                message = "Found reader with id: $readerId"
            )
        )
    }

    @PostMapping("", "/")
    fun createReader(@Valid @RequestBody requestReader: RequestReader): ResponseEntity<ApiResponse<ResponseReader>> {
        val createdReader = readerService.createReader(requestReader)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdReader,
                message = "Reader created successfully"
            )
        )
    }

    @PutMapping("/{readerId}", "/{readerId}/")
    fun updateReader(
        @PathVariable readerId: Long,
        @Valid @RequestBody requestReader: RequestReader
    ): ResponseEntity<ApiResponse<ResponseReader>> {
        val updatedReader = readerService.updateReader(readerId, requestReader)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedReader,
                message = "Reader updated successfully"
            )
        )
    }

    @DeleteMapping("/{readerId}", "/{readerId}/")
    fun deleteReader(@PathVariable readerId: Long): ResponseEntity<ApiResponse<Void>> {
        readerService.deleteReader(readerId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Reader deleted successfully"
            )
        )
    }
}