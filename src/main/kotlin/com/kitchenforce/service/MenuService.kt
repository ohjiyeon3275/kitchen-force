package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.menus.*
import com.kitchenforce.domain.products.entities.ProductRepository
import com.kitchenforce.dto.menus.MenuCreateRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository,
    private val productRepository: ProductRepository,
    private val menuProductRepository: MenuProductRepository
) {

    @Transactional
    fun createMenu(req: MenuCreateRequestDto, menuGroup: Int){
        val group: MenuGroup = menuGroupRepository.findById(menuGroup).orElseThrow()
        val menu: Menu = menuRepository.save(Menu(null, req.name, req.price, menuGroup = group))
        for (product in req.products){
            menuProductRepository.save(
                MenuProduct(null,
                    product.value,
                    menu,
                    productRepository.findById(product.key).orElseThrow()))
        }
    }

    fun findAll(): List<Menu> {
        return menuRepository.findAll()
    }

    fun findById(id: Int): Menu {
        return menuRepository.findByIdOrNull(id)
            ?: throw NotFoundException("메뉴 $id 를 찾을 수 없습니다.")
    }
}
