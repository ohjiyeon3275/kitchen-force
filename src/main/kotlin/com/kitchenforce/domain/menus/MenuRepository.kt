package com.kitchenforce.domain.menus

import org.springframework.data.jpa.repository.JpaRepository

<<<<<<< HEAD
interface MenuRepository : JpaRepository<Menu, Int> {

    fun findByName(name: String): Menu
=======
interface MenuRepository: JpaRepository<Menu, Int> {

    fun findByName(name : String): Menu
>>>>>>> cf9233b (.)
}
