package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuGroup
import com.kitchenforce.domain.menus.MenuGroupRepository
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.dto.menus.MenuCreateRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository
) {

    fun createMenu(req: MenuCreateRequestDto, menuGroup: Int){
        val group: MenuGroup = menuGroupRepository.findById(menuGroup).orElseThrow();
        menuRepository.save(Menu(null, req.name, req.price, menuGroup = group));
    }

    fun findAll(): List<Menu> {
        return menuRepository.findAll()
    }

    fun findById(id: Int): Menu {
        return menuRepository.findByIdOrNull(id)
            ?: throw NotFoundException("메뉴 $id 를 찾을 수 없습니다.")
    }
}
