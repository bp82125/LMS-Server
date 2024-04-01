package com.ct467.libmansys.system

import com.ct467.libmansys.exceptions.*

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AccountStatusException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
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

    @ExceptionHandler(value = [EntityAlreadyAssociatedException::class, CheckoutDetailAlreadyExistsException::class])
    @ResponseBody
    fun handleEntityAlreadyAssociatedException(ex: Exception): ResponseEntity<ApiResponse<Void>> {
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

    @ExceptionHandler(AdminAccountDeletionException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handleAdminAccountDeletionException(ex: AdminAccountDeletionException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ApiResponse(
                flag = false,
                statusCode = HttpStatus.FORBIDDEN.value(),
                message = ex.message ?: "Deleting admin account is not allowed"
            )
        )
    }

    @ExceptionHandler(LibraryCardExpiredException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleLibraryCardExpiredException(ex: LibraryCardExpiredException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                flag = false,
                statusCode = HttpStatus.BAD_REQUEST.value(),
                message = ex.message ?: "Library card is expired and cannot be used to borrow books"
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

    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleMissingServletRequestParameterException(ex: MissingServletRequestParameterException): ResponseEntity<ApiResponse<Void>> {
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            message = "Required request parameter is missing",
            error = listOf(FieldErrorDto(ex.parameterName, ex.message ?: "Missing parameter"))
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Void>> {
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            message = "Json parsing error, please check the request body again.",
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Void>> {
        val apiResponse = ApiResponse<Void>(
            flag = false,
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message
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

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<ApiResponse<Unit>> {
        val responseBody = ApiResponse<Unit>(
            flag = false,
            statusCode = HttpStatus.UNAUTHORIZED.value(),
            data = null,
            message = ex.message ?: "Unauthorized."
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody)
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