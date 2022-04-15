package com.kitchenforce.controller.menu

import com.fasterxml.jackson.databind.ObjectMapper
import com.kitchenforce.configuration.ObjectMapperConfig
import com.kitchenforce.configuration.SlangDictionaryConfig
import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuGroup
import com.kitchenforce.dto.menus.MenuCreateRequestDto
import com.kitchenforce.dto.menus.MenuProductRequestDto
import com.kitchenforce.restdoc.BOOLEAN
import com.kitchenforce.restdoc.NUMBER
import com.kitchenforce.restdoc.OBJECT
import com.kitchenforce.restdoc.STRING
import com.kitchenforce.restdoc.type
import com.kitchenforce.restdoc.wrappedResponseFields
import com.kitchenforce.service.menu.MenuGroupService
import com.kitchenforce.service.menu.MenuService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = [ObjectMapperConfig::class, SlangDictionaryConfig::class])
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@EnableAutoConfiguration
@Import(ObjectMapperConfig::class, SlangDictionaryConfig::class)
@SpringBootTest(classes = [MenuController::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuControllerTest @Autowired constructor(
    val commonObjectMapper: ObjectMapper
) {
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var menuService: MenuService

    @MockkBean
    private lateinit var menuGroupService: MenuGroupService

    private val testMenuGroup = MenuGroup(
        id = 1,
        name = "menu-group",
    )
    private val testProductDto = MenuProductRequestDto(
        productId = 1L,
        number = 1,
    )

    private val testMenuDto = MenuCreateRequestDto(
        name = "menu1",
        price = 50_000,
        products = listOf(testProductDto)
    )

    private val invalidTestMenuDto = """
        {
            "name":"menu1",
            "price":1000
        }
    """.trimIndent()

    private val testResultMenus = listOf(
        Menu(
            id = 1,
            name = "menu1",
            price = 5000,
            hidden = false,
            menuGroup = testMenuGroup
        ),
        Menu(
            id = 2,
            name = "menu2",
            price = 5000,
            hidden = true,
            menuGroup = testMenuGroup
        ),
        Menu(
            id = 3,
            name = "menu3",
            price = 5000,
            hidden = false,
            menuGroup = testMenuGroup
        )
    )

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            .build()
    }

    @Test
    @DisplayName("하나 이상의상품으로 메뉴 생성 테스트")
    fun createMenuTest() {
        every { menuService.createMenu(any(), 1) } returns Unit
        mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/menus/{menu-group}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(commonObjectMapper.writeValueAsString(testMenuDto))
        ).andExpect(
            MockMvcResultMatchers.status().isCreated
        ).andDo(
            MockMvcResultHandlers.print()
        ).andDo(
            document(
                "create-menu",
                pathParameters(
                    parameterWithName("menu-group").description("메뉴 그룹 ID")
                ),
            )
        )
    }

    @Test
    @DisplayName("메뉴 생성시 Validation 체크")
    fun createMenuValidationFailTest() {
        every { menuService.createMenu(any(), 1) } returns Unit
        mockMvc.post("/api/menus/{menu-group}", 1) {
            contentType = MediaType.APPLICATION_JSON
            content = invalidTestMenuDto
        }
            .andExpect {
                status { isBadRequest() }
            }
            .andDo {
                print()
            }
            .andDo {
                document(
                    "create-menu-validation",
                    pathParameters(
                        parameterWithName("menu-group-id").description("메뉴 그룹 ID")
                    ),
                )
            }
    }

    @Test
    @DisplayName("메뉴 목록 반환 테스트 테스트")
    fun findMenuListTest() {
        every { menuService.findAll() } returns testResultMenus

        mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/menus/")
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andDo(
            MockMvcResultHandlers.print()
        ).andDo(
            document(
                "get-menu-list",
                wrappedResponseFields(
                    "[].id" type NUMBER means "메뉴 ID" isOptional false,
                    "[].name" type STRING means "메뉴 이름" isOptional false,
                    "[].price" type NUMBER means "가격" isOptional false,
                    "[].hidden" type BOOLEAN means "숨김 여부" isOptional false,
                    "[].menuGroup" type OBJECT means "메뉴그룹" isOptional false,
                    "[].menuGroup.id" type NUMBER means "메뉴그룹 아이디" isOptional false,
                    "[].menuGroup.name" type STRING means "메뉴그룹 명" isOptional false,
                )
            )
        )
    }
}
