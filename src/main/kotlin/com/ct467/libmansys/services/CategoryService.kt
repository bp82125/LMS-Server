package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestCategory
import com.ct467.libmansys.dtos.ResponseCategory
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.repositories.CategoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class CategoryService(
    @Autowired private val categoryRepository: CategoryRepository
) {
    fun findAllCategories(status: String? = null): List<ResponseCategory> {
        return when (status) {
            "available" -> categoryRepository.findAllByDeletedFalse().map { it.toResponse() }
            "deleted" -> categoryRepository.findAllByDeletedTrue().map { it.toResponse() }
            "all" -> categoryRepository.findAll().map { it.toResponse() }
            else -> categoryRepository.findAll().map { it.toResponse() }
        }
    }

    fun findCategoryById(id: Long): ResponseCategory {
        val category = categoryRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }
            .also { if(it.deleted) throw EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }

        return category.toResponse()
    }

    fun createCategory(requestCategory: RequestCategory): ResponseCategory {
        val category = requestCategory.toEntity()
        val createdCategory = categoryRepository.save(category)
        return createdCategory.toResponse()
    }

    fun updateCategory(id: Long, requestCategory: RequestCategory): ResponseCategory {
        categoryRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }
            .also { if(it.deleted) throw EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }

        val category = requestCategory.toEntity(id)
        val updatedCategory = categoryRepository.save(category)
        return updatedCategory.toResponse()
    }

    fun deleteCategory(id: Long){
        val category = categoryRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }
            .also { if(it.deleted) throw EntityWithIdNotFoundException(objectName =  "Category", id = "$id") }

        categoryRepository.save(
            category.apply { this.deleted = true }
        )
    }
}