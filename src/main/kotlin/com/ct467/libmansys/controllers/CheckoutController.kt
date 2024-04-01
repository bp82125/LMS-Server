package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestCheckout
import com.ct467.libmansys.dtos.ResponseCheckout
import com.ct467.libmansys.services.CheckoutService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.endpoint.base-url}/checkouts")
class CheckoutController(
    @Autowired private val checkoutService: CheckoutService
) {
    @GetMapping("/totals", "/totals/")
    fun countAllCheckouts(): ResponseEntity<ApiResponse<List<Any>>> {
        val totals = checkoutService.countTotal()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = totals,
                message = "Found total count of checkouts"
            )
        )
    }

    @GetMapping("", "/")
    fun findAllCheckouts(): ResponseEntity<ApiResponse<List<ResponseCheckout>>> {
        val checkouts = checkoutService.findAllCheckouts()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = checkouts,
                message = "Found checkouts"
            )
        )
    }

    @GetMapping("/{id}", "/{id}/")
    fun findCheckoutById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<ResponseCheckout>> {
        val checkout = checkoutService.findCheckoutById(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = checkout,
                message = "Found checkout with id: $id"
            )
        )
    }

    @PostMapping("", "/")
    fun createCheckout(@RequestBody @Valid requestCheckout: RequestCheckout): ResponseEntity<ApiResponse<ResponseCheckout>> {
        val createdCheckout = checkoutService.createCheckout(requestCheckout)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdCheckout,
                message = "Created a checkout"
            )
        )
    }

    @PutMapping("/{id}", "/{id}/")
    fun updateCheckout(
        @PathVariable id: Long,
        @Valid @RequestBody requestCheckout: RequestCheckout
    ): ResponseEntity<ApiResponse<ResponseCheckout>> {
        val updatedCheckout = checkoutService.updateCheckout(id, requestCheckout)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedCheckout,
                message = "Checkout updated successfully"
            )
        )
    }

    @DeleteMapping("/{id}", "/{id}/")
    fun deleteCheckout(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        checkoutService.deleteCheckout(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Checkout deleted successfully"
            )
        )
    }

}