package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.menus.*
import com.kitchenforce.domain.products.entities.ProductRepository
import com.kitchenforce.dto.menus.MenuCreateRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository,
    private val productRepository: ProductRepository,
    private val menuProductRepository: MenuProductRepository
) {

    fun createMenu(req: MenuCreateRequestDto, menuGroup: Int){
        val group: MenuGroup = menuGroupRepository.findById(menuGroup).orElseThrow()
        val menu: Menu = menuRepository.save(Menu(null, req.name, req.price, menuGroup = group))
//        val arr : MutableList<Product>?=null
        for (i in req.products){
//            arr?.add(productRepository.findById(i).orElseThrow()
            menuProductRepository.save(
                MenuProduct(null, null,
                    menu,
                    productRepository.findById(i).orElseThrow()))
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
