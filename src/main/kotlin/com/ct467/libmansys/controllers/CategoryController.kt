package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestCategory
import com.ct467.libmansys.dtos.ResponseCategory
import com.ct467.libmansys.services.CategoryService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/categories")
class CategoryController(
    @Autowired private val categoryService: CategoryService
) {
    @GetMapping("", "/")
    fun findAllCategories(): ResponseEntity<ApiResponse<List<ResponseCategory>>> {
        val category = categoryService.findAllCategories()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = category,
                message = "Found categories"
            )
        )
    }

    @GetMapping("/{id}")
    fun findCategoryById(@PathVariable id: Long): ResponseEntity<ApiResponse<ResponseCategory>> {
        val category = categoryService.findCategoryById(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = category,
                message = "Found category with id: $id"
            )
        )
    }

    @PostMapping("")
    fun createCategory(@RequestBody @Valid requestCategory: RequestCategory): ResponseEntity<ApiResponse<ResponseCategory>> {
        val createdCategory = categoryService.createCategory(requestCategory)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdCategory,
                message = "Category created successfully"
            )
        )
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody @Valid requestCategory: RequestCategory
    ): ResponseEntity<ApiResponse<ResponseCategory>> {
        val updatedCategory = categoryService.updateCategory(id, requestCategory)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedCategory,
                message = "Category updated successfully"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> {
        categoryService.deleteCategory(id)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Category deleted successfully"
            )
        )
    }
}