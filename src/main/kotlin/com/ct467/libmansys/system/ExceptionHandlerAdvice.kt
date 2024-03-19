package com.ct467.libmansys.system

import com.ct467.libmansys.exceptions.AssociatedEntityNotFoundException
import com.ct467.libmansys.exceptions.EntityAlreadyAssociatedException
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    data class FieldErrorDto(val field: String, val error: String)

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

    @ExceptionHandler(EntityAlreadyAssociatedException::class)
    @ResponseBody
    fun handleEntityAlreadyAssociatedException(ex: EntityAlreadyAssociatedException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                flag = false,
                statusCode = HttpStatus.CONFLICT.value(),
                message = ex.message
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Void>> {
        val errors = ex.bindingResult.fieldErrors.map { fieldError: FieldError ->
            FieldErrorDto(fieldError.field, fieldError.defaultMessage ?: "Validation error")
        }
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            error = errors
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse)
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