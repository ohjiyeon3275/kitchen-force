package com.kitchenforce.service.menu

import com.kitchenforce.domain.menus.MenuGroup
import com.kitchenforce.domain.menus.MenuGroupRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
class MenuGroupServiceTest @Autowired constructor(
    private val menuGroupService: MenuGroupService,
    private val menuGroupRepository: MenuGroupRepository
) {

    private val testMenuGroupList = listOf(
        MenuGroup(
            id = 1,
            name = "group1"
        ),
        MenuGroup(
            id = 2,
            name = "group2"
        ),
        MenuGroup(
            id = 3,
            name = "group3"
        )
    )

    private fun listTestFixture() {
        menuGroupRepository.saveAll(testMenuGroupList)
    }
    @Test
    @DisplayName("MenuGroup 전체 조회 테스트")
    @Transactional
    fun findAllMenuGroup_test() {
        listTestFixture()
        val result = menuGroupService.findAll()
        assertEquals(3, result.size)
        result.forEach {
            assertEquals("group${it.id}", it.name)
        }
    }
}
