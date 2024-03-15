package com.ct467.libmansys.controllers

import com.ct467.libmansys.dtos.RequestEmployee
import com.ct467.libmansys.dtos.ResponseEmployee
import com.ct467.libmansys.services.EmployeeService
import com.ct467.libmansys.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/employees")
class EmployeeController(
    @Autowired private val employeeService: EmployeeService
) {
    @GetMapping("", "/")
    fun findAllEmployees(): ResponseEntity<ApiResponse<List<ResponseEmployee>>> {
        val employees = employeeService.findAllEmployees()
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = employees,
                message = "Found employees"
            )
        )
    }

    @GetMapping("/{employeeId}", "/{employeeId}/")
    fun findEmployeeById(@PathVariable employeeId: Long): ResponseEntity<ApiResponse<ResponseEmployee>> {
        val employee = employeeService.findEmployeeById(employeeId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = employee,
                message = "Found employee with id: $employeeId"
            )
        )
    }

    @PostMapping("", "/")
    fun createEmployee(@Valid @RequestBody requestEmployee: RequestEmployee): ResponseEntity<ApiResponse<ResponseEmployee>> {
        val createdEmployee = employeeService.createEmployee(requestEmployee)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdEmployee,
                message = "Employee created successfully"
            )
        )
    }

    @PutMapping("/{employeeId}", "/{employeeId}/")
    fun updateEmployee(
        @PathVariable employeeId: Long,
        @Valid @RequestBody requestEmployee: RequestEmployee
    ): ResponseEntity<ApiResponse<ResponseEmployee>> {
        val updatedEmployee = employeeService.updateEmployee(employeeId, requestEmployee)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedEmployee,
                message = "Employee updated successfully"
            )
        )
    }

    @DeleteMapping("/{employeeId}", "/{employeeId}/")
    fun deleteEmployee(@PathVariable employeeId: Long): ResponseEntity<ApiResponse<Void>> {
        employeeService.deleteEmployee(employeeId)
        return ResponseEntity.ok(
            ApiResponse(
                flag = true,
                statusCode = HttpStatus.OK.value(),
                message = "Employee deleted successfully"
            )
        )
    }
}