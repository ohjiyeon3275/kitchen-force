package com.kitchenforce.controller

import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.dto.menus.MenuCreateRequestDto
import com.kitchenforce.service.MenuService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Menu")
@RequestMapping("/api/menus")
@RestController
class MenuController(
    private val menuService: MenuService,
) {

    @Operation(summary = "하나 이상의 상품으로 메뉴를 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{menuGroup}")
    fun menuCreate(@RequestBody @Valid req: MenuCreateRequestDto, @PathVariable menuGroup: Int){
        menuService.createMenu(req, menuGroup)
    }

    @Operation(summary = "메뉴 목록을 반환합니다.")
    @GetMapping("/")
    fun index(): List<Menu> {
        return menuService.findAll()
    }
}
