package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestPublisher
import com.ct467.libmansys.dtos.ResponsePublisher
import com.ct467.libmansys.services.PublisherService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/publishers")
class PublisherController(
    @Autowired private val publisherService: PublisherService
) {
    @GetMapping("", "/")
    fun findAllPublishers(
        @RequestParam(required = false, defaultValue = "available") status: String?,
    ): ResponseEntity<ApiResponse<List<ResponsePublisher>>> {
        val publishers = publisherService.findAllPublishers(status)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = publishers,
                message = "Found Publishers"
            )
        )
    }

    @GetMapping("/{id}", "/{id}/")
    fun findPublisherById(@PathVariable id: Long): ResponseEntity<ApiResponse<ResponsePublisher>> {
        val publisher = publisherService.findPublisherById(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = publisher,
                message = "Found publisher with id: $id"
            )
        )
    }

    @PostMapping("", "/")
    fun createPublisher(@Valid @RequestBody requestPublisher: RequestPublisher
    ): ResponseEntity<ApiResponse<ResponsePublisher>> {
        val createdPublisher = publisherService.createPublisher(requestPublisher)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdPublisher,
                message = "Publisher created successfully"
            )
        )
    }

    @PutMapping("/{id}", "/{id}/")
    fun updatePublisher(
        @PathVariable id: Long,
        @Valid @RequestBody requestPublisher: RequestPublisher
    ): ResponseEntity<ApiResponse<ResponsePublisher>> {
        val updatedPublisher = publisherService.updatePublisher(id, requestPublisher)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedPublisher,
                message = "Publisher updated successfully"
            )
        )
    }

    @DeleteMapping("/{id}", "/{id}/")
    fun deletePublisher(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> {
        publisherService.deletePublisher(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Publisher deleted successfully"
            )
        )
    }
}