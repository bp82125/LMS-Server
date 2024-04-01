package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestCheckoutDetail
import com.ct467.libmansys.dtos.RequestCheckoutDetailForList
import com.ct467.libmansys.dtos.ResponseCheckoutDetail
import com.ct467.libmansys.exceptions.CheckoutDetailAlreadyExistsException
import com.ct467.libmansys.exceptions.EntityAlreadyAssociatedException
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
    fun countTotal(checkoutId: Long): Int {
        return checkoutDetailRepository.findAllByCheckout_Id(checkoutId).count()
    }

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
            .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Book", id = "$bookId") }

        if (checkoutDetailRepository.existsByCheckout_IdAndBook_Id(checkoutId, bookId)) {
            throw CheckoutDetailAlreadyExistsException(checkoutId, bookId)
        }

        val checkoutDetail = requestCheckoutDetail.toEntity(checkout, book)
        val createdCheckoutDetail = checkoutDetailRepository.save(checkoutDetail)
        return createdCheckoutDetail.toResponse()
    }

    fun createMultipleCheckoutDetails(checkoutId: Long, requestCheckoutDetailForLists: List<RequestCheckoutDetailForList>): List<ResponseCheckoutDetail> {
        val checkout = checkoutRepository
            .findById(checkoutId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Checkout", id = "$checkoutId") }

        val createdDetails = mutableListOf<ResponseCheckoutDetail>()

        for (requestDetail in requestCheckoutDetailForLists) {
            val bookId = requestDetail.bookId
            val book = bookRepository
                .findById(bookId)
                .orElseThrow { EntityWithIdNotFoundException(objectName = "Book", id = "$bookId") }
                .also { if (it.deleted) throw EntityWithIdNotFoundException(objectName = "Book", id = "$bookId") }

            if (checkoutDetailRepository.existsByCheckout_IdAndBook_Id(checkoutId, bookId)) {
                throw CheckoutDetailAlreadyExistsException(checkoutId, bookId)
            }

            val checkoutDetail = requestDetail.toEntity(checkout, book)
            val createdCheckoutDetail = checkoutDetailRepository.save(checkoutDetail)
            createdDetails.add(createdCheckoutDetail.toResponse())
        }

        return createdDetails
    }

    fun updateCheckoutDetail(checkoutId: Long, bookId: Long, requestCheckoutDetail: RequestCheckoutDetail): ResponseCheckoutDetail {
        val checkoutDetail = checkoutDetailRepository
            .findByCheckout_IdAndBook_Id(checkoutId, bookId)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Checkout detail", id = "checkout_id: $checkoutId, book_id: $bookId") }
            .apply {
                note = requestCheckoutDetail.note.toString()
                if (!returned && requestCheckoutDetail.returned) {
                    returned = true
                    returnDate = LocalDate.now()
                } else {
                    returned = false
                    returnDate = null
                }
            }
        val updatedCheckoutDetail = checkoutDetailRepository.save(checkoutDetail)
        return updatedCheckoutDetail.toResponse()
    }

    fun deleteCheckoutDetail(checkoutId: Long, bookId: Long){
        if(!checkoutDetailRepository.existsByCheckout_IdAndBook_Id(checkoutId, bookId)){
            throw EntityWithIdNotFoundException(objectName = "Checkout detail", id = "checkout_id: $checkoutId, book_id: $bookId")
        }

        return checkoutDetailRepository.deleteByCheckout_IdAndBook_Id(checkoutId, bookId)
    }

}