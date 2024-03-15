package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestCheckoutDetail
import com.ct467.libmansys.dtos.ResponseCheckoutDetail
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.models.compositekeys.CheckoutDetailId
import com.ct467.libmansys.repositories.BookRepository
import com.ct467.libmansys.repositories.CheckoutDetailRepository
import com.ct467.libmansys.repositories.CheckoutRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class CheckoutDetailService(
    @Autowired private val checkoutDetailRepository: CheckoutDetailRepository,
    @Autowired private val checkoutRepository: CheckoutRepository,
    @Autowired private val bookRepository: BookRepository
) {
    fun findAllDetails(checkoutId: Long): List<ResponseCheckoutDetail> {
        return checkoutDetailRepository.findAllByCheckout_Id(checkoutId).map { it.toResponse() }
    }

    fun findDetailByIds(checkoutId: Long, bookId: Long): ResponseCheckoutDetail {
        val checkoutDetail = checkoutDetailRepository
            .findById(CheckoutDetailId(checkoutId, bookId))
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Checkout detail", id = "checkout_id: $checkoutId, book_id: $bookId") }

        return checkoutDetail.toResponse()
    }

    fun createCheckoutDetail(checkoutId: Long, bookId: Long, requestCheckoutDetail: RequestCheckoutDetail): ResponseCheckoutDetail {
        val checkout = checkoutRepository
            .findById(checkoutId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Checkout", id = "$checkoutId") }

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Book", id = "$bookId") }

        val checkoutDetail = requestCheckoutDetail.toEntity(checkout, book)
        val createdCheckoutDetail = checkoutDetailRepository.save(checkoutDetail)
        return createdCheckoutDetail.toResponse()
    }

    fun updateCheckoutDetail(checkoutId: Long, bookId: Long, requestCheckoutDetail: RequestCheckoutDetail): ResponseCheckoutDetail {
        val checkoutDetail = checkoutDetailRepository
            .findById(CheckoutDetailId(checkoutId, bookId))
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Checkout detail", id = "checkout_id: $checkoutId, book_id: $bookId") }
            .apply {
                note = requestCheckoutDetail.note.toString()
                if (!returned && requestCheckoutDetail.returned) {
                    returned = true
                    returnDate = LocalDate.now()
                }
            }
        val updatedCheckoutDetail = checkoutDetailRepository.save(checkoutDetail)
        return updatedCheckoutDetail.toResponse()
    }

    fun deleteCheckoutDetail(checkoutId: Long, bookId: Long){
        if(!checkoutDetailRepository.existsById(CheckoutDetailId(checkoutId, bookId))){
            throw EntityWithIdNotFoundException(objectName = "Checkout detail", id = "checkout_id: $checkoutId, book_id: $bookId")
        }

        return checkoutDetailRepository.deleteById(CheckoutDetailId(checkoutId, bookId))
    }
}