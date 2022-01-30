package com.kitchenforce.domain.menus

import com.kitchenforce.domain.orders.OrderTable
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository: JpaRepository<Menu, Int> {

    fun findByName(menuName : String): Menu
}