package com.kitchenforce.controller.product

import com.kitchenforce.controller.product.ProductController.Companion.PRODUCT_API_PATH
import com.kitchenforce.domain.products.dtos.ProductDto
import com.kitchenforce.service.product.ProductService
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports
import javax.validation.Valid

@RestController
@RequestMapping(PRODUCT_API_PATH)
class ProductController(
    private val service: ProductService,
) {
    companion object {
        const val PRODUCT_API_PATH = "/api/products"
    }
    @GetMapping
    fun index(): List<ProductDto> {
        return service.findAll()
    }

    @PostMapping
    fun create(@Valid @RequestBody vo: ProductDto): ProductDto {
        return service.create(vo)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody data: ProductDto): ProductDto {
        return service.update(id, data)
    }
}
