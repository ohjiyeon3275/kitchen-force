package com.kitchenforce.service.product

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.common.utils.SlangDictionary
import com.kitchenforce.domain.products.dtos.ProductDto
import com.kitchenforce.domain.products.entities.Product
import com.kitchenforce.domain.products.entities.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val slangDictionary: SlangDictionary,
) {
    fun findAll(): List<ProductDto> {
        return productRepository.findAll().map { it.toDto() }
    }

    private fun findById(id: Long): Product {
        return productRepository.findByIdOrNull(id)
            ?: throw NotFoundException("상품 $id 를 찾을 수 없습니다.")
    }

    @Transactional
    fun create(dto: ProductDto): ProductDto {
        return productRepository.save(Product.fromDto(dto, slangDictionary)).toDto()
    }

    @Transactional
    fun update(id: Long, dto: ProductDto): ProductDto =
        findById(id).run {
            this.slangDictionary = slangDictionary
            this.name = dto.name
            this.price = dto.price
            productRepository.save(this)
        }.toDto()
}
