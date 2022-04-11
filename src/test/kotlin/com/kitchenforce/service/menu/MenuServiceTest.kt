package com.kitchenforce.service.menu

import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuGroup
import com.kitchenforce.domain.menus.MenuGroupRepository
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.OrderMenuRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class MenuServiceTest @Autowired constructor(
    private val menuService: MenuService,
    private val menuRepository: MenuRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuGroupRepository: MenuGroupRepository
) {

    companion object {
        const val MENU_GROUP_NAME1 = "Menu-Group-1"
        const val MENU_GROUP_NAME2 = "Menu-Group-2"
        const val MENU_NAME1 = "Menu-1"
        const val MENU_NAME2 = "Menu-2"
        const val MENU_NAME3 = "Menu-3"
    }

    private val testMenuGroups = listOf(
        MenuGroup(
            id = null,
            name = MENU_GROUP_NAME1
        ),
        MenuGroup(
            id = null,
            name = MENU_GROUP_NAME2
        )
    ).run {
        menuGroupRepository.saveAll(this)
    }

    private val testMenuList = listOf(
        Menu(
            id = null,
            name = MENU_NAME1,
            price = 10_000,
            hidden = false,
            menuGroup = testMenuGroups.first { it.name == MENU_GROUP_NAME1 }
        ),
        Menu(
            id = null,
            name = MENU_NAME2,
            price = 10_000,
            hidden = false,
            menuGroup = testMenuGroups.first { it.name == MENU_GROUP_NAME2 }
        ),
        Menu(
            id = null,
            name = MENU_NAME3,
            price = 10_000,
            hidden = true,
            menuGroup = testMenuGroups.first { it.name == MENU_GROUP_NAME1 }
        ),
    )

    @BeforeEach
    fun setUp() {
        menuRepository.saveAll(testMenuList)
    }

    @Test
    @DisplayName("전체 조회 테스트")
    fun findAllTest() {
        val result = menuService.findAll()
        assertEquals(testMenuList.size - 1, result.size)
    }
}
