package com.ct467.libmansys.system

import com.ct467.libmansys.exceptions.*
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AccountStatusException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    data class FieldErrorDto(val field: String, val error: String)

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(
                ApiResponse(
                    flag = false,
                    statusCode = HttpStatus.FORBIDDEN.value(),
                    data = ex.message,
                    message = "No permission"
                )
            )
    }

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

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleUsernameAlreadyExistsException(ex: UsernameAlreadyExistsException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                flag = false,
                statusCode = HttpStatus.CONFLICT.value(),
                message = ex.message
            )
        )
    }

    @ExceptionHandler(EntityAssociationException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleEntityAssociationException(ex: EntityAssociationException): ResponseEntity<ApiResponse<Void>> {
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

    @ExceptionHandler(value = [UsernameNotFoundException::class, BadCredentialsException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleAuthenticationException(ex: Exception) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ApiResponse(
                    flag = false,
                    statusCode = HttpStatus.UNAUTHORIZED.value(),
                    data = ex.message,
                    message = "Username or password is incorrect."
                )
            )
    }

    @ExceptionHandler(AccountStatusException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleAccountException(ex: AccountStatusException) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ApiResponse(
                    flag = false,
                    statusCode = HttpStatus.UNAUTHORIZED.value(),
                    data = ex.message,
                    message = "User account is abnormal."
                )
            )
    }

    @ExceptionHandler(InvalidBearerTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleInvalidBearerTokenException(ex: InvalidBearerTokenException) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ApiResponse(
                    flag = false,
                    statusCode = HttpStatus.UNAUTHORIZED.value(),
                    data = ex.message,
                    message = "The access token provided is expired, revoked, malformed, invalid for other reasons."
                )
            )
    }


}