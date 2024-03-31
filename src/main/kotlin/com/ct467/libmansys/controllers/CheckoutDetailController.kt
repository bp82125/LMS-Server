package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestCheckoutDetail
import com.ct467.libmansys.dtos.RequestCheckoutDetailForList
import com.ct467.libmansys.dtos.ResponseCheckoutDetail
import com.ct467.libmansys.models.CheckoutDetail
import com.ct467.libmansys.services.CheckoutDetailService
import com.ct467.libmansys.system.ApiResponse
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
class CheckoutDetailController (
    @Autowired private val checkoutDetailService: CheckoutDetailService
) {
    @GetMapping("/{checkoutId}/details", "/{checkoutId}/details/")
    fun findAllDetailsOfCheckout(@PathVariable checkoutId: Long) : ResponseEntity<ApiResponse<List<CheckoutDetail>>> {
        val details = checkoutDetailService.findAllDetails(checkoutId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = details,
                message = "Found all details of checkout with id: $checkoutId"
            )
        )
    }

    @GetMapping("/{checkoutId}/details/{bookId}", "/{checkoutId}/details/{bookId}/")
    fun findDetailByCheckout(
        @PathVariable checkoutId: Long,
        @PathVariable bookId: Long
    ): ResponseEntity<ApiResponse<ResponseCheckoutDetail>> {
        val detail = checkoutDetailService.findDetailByIds(checkoutId, bookId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = detail,
                message = "Found detail with checkoutId: $checkoutId and bookId: $bookId"
            )
        )
    }

    @PostMapping("/{checkoutId}/details/{bookId}", "/{checkoutId}/details/{bookId}/")
    fun createCheckoutDetail(
        @PathVariable checkoutId: Long,
        @PathVariable bookId: Long,
        @RequestBody requestCheckoutDetail: RequestCheckoutDetail
    ): ResponseEntity<ApiResponse<ResponseCheckoutDetail>> {
        val createdDetail = checkoutDetailService.createCheckoutDetail(checkoutId, bookId, requestCheckoutDetail)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdDetail,
                message = "Created detail of checkoutId: $checkoutId and bookId: $bookId"
            )
        )
    }

    @PostMapping("/{checkoutId}/details", "/{checkoutId}/details/")
    fun createMultipleCheckoutDetails(
        @PathVariable checkoutId: Long,
        @RequestBody requestCheckoutDetailForLists: List<RequestCheckoutDetailForList>
    ): ResponseEntity<ApiResponse<List<ResponseCheckoutDetail>>> {
        val createdDetails = checkoutDetailService.createMultipleCheckoutDetails(checkoutId, requestCheckoutDetailForLists)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdDetails,
                message = "Created ${createdDetails.size} detail(s) for checkoutId: $checkoutId"
            )
        )
    }

    @PutMapping("/{checkoutId}/details/{bookId}", "/{checkoutId}/details/{bookId}/")
    fun updateCheckoutDetail(
        @PathVariable checkoutId: Long,
        @PathVariable bookId: Long,
        @RequestBody requestCheckoutDetail: RequestCheckoutDetail
    ): ResponseEntity<ApiResponse<ResponseCheckoutDetail>> {
        val updatedDetail = checkoutDetailService.updateCheckoutDetail(checkoutId, bookId, requestCheckoutDetail)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedDetail,
                message = "Updated detail of checkoutId: $checkoutId and bookId: $bookId"
            )
        )
    }

    @DeleteMapping("/{checkoutId}/details/{bookId}", "/{checkoutId}/details/{bookId}/")
    fun deleteCheckoutDetail(
        @PathVariable checkoutId: Long,
        @PathVariable bookId: Long,
    ): ResponseEntity<ApiResponse<Unit>> {
        checkoutDetailService.deleteCheckoutDetail(checkoutId, bookId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Checkout detail deleted successfully"
            )
        )
    }
}