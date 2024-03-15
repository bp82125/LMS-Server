package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestCheckout
import com.ct467.libmansys.dtos.ResponseCheckout
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.CheckoutRepository
import com.ct467.libmansys.repositories.EmployeeRepository
import com.ct467.libmansys.repositories.LibraryCardRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class CheckoutService(
    @Autowired private val checkoutRepository: CheckoutRepository,
    @Autowired private val libraryCardRepository: LibraryCardRepository,
    @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findAllCheckouts(): List<ResponseCheckout> {
        return checkoutRepository.findAll().map { it.toResponse() }
    }

    fun findCheckoutById(id: Long): ResponseCheckout {
        val checkout = checkoutRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Checkout", id = "$id") }

        return checkout.toResponse()
    }

    fun createCheckout(requestCheckout: RequestCheckout): ResponseCheckout {
        val libraryCard = libraryCardRepository.findById(requestCheckout.cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Library card", id = "$requestCheckout.cardNumber") }

        val employee = employeeRepository
            .findById(requestCheckout.employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$requestCheckout.employeeId") }

        val checkout = requestCheckout.toEntity(libraryCard = libraryCard, employee = employee)
        val createdCheckout = checkoutRepository.save(checkout)
        return createdCheckout.toResponse()
    }

    fun updateCheckout(id: Long, requestCheckout: RequestCheckout): ResponseCheckout {
        if (!checkoutRepository.existsById(id)){
            throw EntityWithIdNotFoundException(objectName =  "Checkout", id = "$id")
        }

        val libraryCard = libraryCardRepository.findById(requestCheckout.cardNumber)
            .orElseThrow { EntityWithIdNotFoundException(objectName = "Library card", id = "$requestCheckout.cardNumber") }

        val employee = employeeRepository
            .findById(requestCheckout.employeeId)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Employee", id = "$requestCheckout.employeeId") }

        val checkout = requestCheckout
            .toEntity(libraryCard = libraryCard, employee = employee)
            .apply { this.id = id }

        val updatedCheckout = checkoutRepository.save(checkout)
        return updatedCheckout.toResponse()
    }

    fun deleteCheckout(id: Long){
        if (!checkoutRepository.existsById(id)){
            throw EntityWithIdNotFoundException(objectName =  "Checkout", id = "$id")
        }

        return checkoutRepository.deleteById(id)
    }

}