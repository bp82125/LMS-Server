package com.ct467.libmansys.system

import com.ct467.libmansys.exceptions.AssociatedEntityNotFoundException
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(EntityWithIdNotFoundException::class)
    @ResponseBody
    fun handleEntityWithIdNotFoundException(ex: EntityWithIdNotFoundException): ResponseEntity<ApiResponse<Void>> {
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse)
    }

    @ExceptionHandler(AssociatedEntityNotFoundException::class)
    @ResponseBody
    fun handleAssociatedEntityNotFoundException(ex: AssociatedEntityNotFoundException): ResponseEntity<ApiResponse<Void>> {
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Void>> {
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "An unexpected error occurred: ${ex.message}"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse)
    }
}