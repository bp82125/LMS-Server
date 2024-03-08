package com.ct467.libmansys.services

import com.ct467.libmansys.converters.toEntity
import com.ct467.libmansys.converters.toResponse
import com.ct467.libmansys.dtos.RequestCategory
import com.ct467.libmansys.dtos.ResponseCategory
import com.ct467.libmansys.exceptions.EntityWithIdNotFoundException
import com.ct467.libmansys.models.Category
import com.ct467.libmansys.repositories.CategoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class CategoryService(
    @Autowired private val categoryRepository: CategoryRepository
) {
    fun findAllCategories(): List<ResponseCategory> {
        return categoryRepository
            .findAll()
            .map { category -> category.toResponse() }
    }

    fun findCategoryById(id: Long): ResponseCategory {
        val category = categoryRepository
            .findById(id)
            .orElseThrow { EntityWithIdNotFoundException(objectName =  "Category", id = id) }

        return category.toResponse()
    }

    fun createCategory(requestCategory: RequestCategory): ResponseCategory {
        val category = requestCategory.toEntity()
        val createdCategory = categoryRepository.save(category)
        return createdCategory.toResponse()
    }

    fun updateCategory(id: Long, requestCategory: RequestCategory): ResponseCategory {
        if (!categoryRepository.existsById(id)) {
            throw EntityWithIdNotFoundException("Category", id)
        }

        val category = requestCategory.toEntity(id)
        val updatedCategory = categoryRepository.save(category)
        return updatedCategory.toResponse()
    }

    fun deleteCategory(id: Long){
        if (!categoryRepository.existsById(id)) {
            throw EntityWithIdNotFoundException("Category", id)
        }

        return categoryRepository.deleteById(id)
    }
}