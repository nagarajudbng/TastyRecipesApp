package com.dbng.presentation.viewmodel

import com.dbng.core.domain.Resource
import com.dbng.core.domain.utils.ResponseError
import com.dbng.presentation.ui.menu.utils.MenuUIState
import com.dbng.presentation.ui.menu.viewmodel.MenuViewModel
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.reflect.Method


// Created by Nagaraju on 13/11/24.

@OptIn(ExperimentalCoroutinesApi::class)
class MenuViewModelTest {


    private lateinit var viewModel: com.dbng.presentation.ui.menu.viewmodel.MenuViewModel
    private lateinit var repository: com.dbng.domain.repository.MenuRepository
    private lateinit var fetchMenuItemsUseCase: com.dbng.domain.usecase.FetchMenuItemsUseCase
    private lateinit var menuItemMoreInfoUseCase: com.dbng.domain.usecase.MenuItemMoreInfoUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        repository = mock(com.dbng.domain.repository.MenuRepository::class.java)
        fetchMenuItemsUseCase = com.dbng.domain.usecase.FetchMenuItemsUseCase(repository)
        menuItemMoreInfoUseCase = com.dbng.domain.usecase.MenuItemMoreInfoUseCase(repository)
        viewModel = com.dbng.presentation.ui.menu.viewmodel.MenuViewModel(
            fetchMenuItemsUseCase,
            menuItemMoreInfoUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMenuState UI state`() = runTest(testDispatcher){
        assertEquals(MenuUIState.Loading, viewModel.menuState.value)
    }



    @Test
    fun `fetchMenuList return success`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        val mockMenuItems = listOf(
            com.dbng.domain.model.MenuItem(
                id = 1,
                name = "Pizza",
                imageURL = "url",
                description = "test",
                price = 20,
                quantity = 1,
                menuType = "a",
                category = "b",
                subCategory = "c",
                itemType = "d",
                ingredients = "e"
            ),
            com.dbng.domain.model.MenuItem(
                id = 1,
                name = "Burger",
                imageURL = "url",
                description = "test",
                price = 20,
                quantity = 1,
                menuType = "a",
                category = "b",
                subCategory = "c",
                itemType = "d",
                ingredients = "e"
            ),
        )
        `when`(fetchMenuItemsUseCase(from,size)).thenReturn(Resource.Success(mockMenuItems))
        viewModel.fetchMenuList()
        assertEquals(MenuUIState.Success, viewModel.menuState.value)
        assertEquals(mockMenuItems, viewModel.items.value)
    }

    @Test
    fun `fetchMenuList return failure with IOException`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        `when`(fetchMenuItemsUseCase(from,size)).thenReturn(Resource.Error(null,responseError = ResponseError.NetworkError))
        viewModel.fetchMenuList()
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Network Error", (viewModel.menuState.value as MenuUIState.Error).message)

    }
    @Test
    fun `fetchMenuList return failure with HttpException`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        `when`(fetchMenuItemsUseCase(from,size)).thenReturn(Resource.Error(null,responseError = ResponseError.ServerError))
        viewModel.fetchMenuList()
        advanceUntilIdle()
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Server Error", (viewModel.menuState.value as MenuUIState.Error).message)

    }

    @Test
    fun `fetchMenuList return failure`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        `when`(fetchMenuItemsUseCase(from,size)).thenReturn(Resource.Error(null,responseError = ResponseError.UnknownError))
        viewModel.fetchMenuList()
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Unknown Error", (viewModel.menuState.value as MenuUIState.Error).message)

    }
    @Test
    fun `fetchMenuList return none`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        `when`(fetchMenuItemsUseCase(from,size)).thenReturn(Resource.Error(null,responseError = null))
        viewModel.fetchMenuList()
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Unknown Error", (viewModel.menuState.value as MenuUIState.Error).message)
    }

    @Test
    fun `fetchMenuList return NoDataFoundError`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        `when`(fetchMenuItemsUseCase(from,size)).thenReturn(Resource.Error(null,responseError = ResponseError.NoDataFoundError))
        viewModel.fetchMenuList()
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("No More Data", (viewModel.menuState.value as MenuUIState.Error).message)
    }


    @Test
    fun `fetchMenuItemDetails return success`() = runTest(testDispatcher)  {
        var itemID = 123
        val mockMenuItems = com.dbng.domain.model.MenuItem(
            id = 123,
            name = "Pizza",
            imageURL = "url",
            description = "test",
            price = 20,
            quantity = 1,
            menuType = "a",
            category = "b",
            subCategory = "c",
            itemType = "d",
            ingredients = "e"
        )
        `when`(menuItemMoreInfoUseCase(itemID)).thenReturn(Resource.Success(mockMenuItems))
        viewModel.fetchMenuItemDetails(itemID)
        assertEquals(MenuUIState.Success, viewModel.menuState.value)
        assertEquals(mockMenuItems, viewModel.detailItems.value)
    }
    @Test
    fun `fetchMenuItemDetails return failure with IOException`() = runTest(testDispatcher)  {
        var itemID = 123
        `when`(menuItemMoreInfoUseCase(itemID)).thenReturn(Resource.Error(null,responseError = ResponseError.NetworkError))
        viewModel.fetchMenuItemDetails(itemID)
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Network Error", (viewModel.menuState.value as MenuUIState.Error).message)

    }
    @Test
    fun `fetchMenuItemDetails return failure with HttpException`() = runTest(testDispatcher) {
        var itemID = 123
        `when`(menuItemMoreInfoUseCase(itemID)).thenReturn(Resource.Error(null,responseError = ResponseError.ServerError))
        viewModel.fetchMenuItemDetails(itemID)
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Server Error", (viewModel.menuState.value as MenuUIState.Error).message)

    }

    @Test
    fun `fetchMenuItemDetails return failure`() = runTest(testDispatcher) {
        var itemID = 123
        `when`(menuItemMoreInfoUseCase(itemID)).thenReturn(Resource.Error(null,responseError = ResponseError.UnknownError))
        viewModel.fetchMenuItemDetails(itemID)
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Unknown Error", (viewModel.menuState.value as MenuUIState.Error).message)

    }
    @Test
    fun `fetchMenuItemDetails return none`() = runTest(testDispatcher)  {
        var itemID = 123
        `when`(menuItemMoreInfoUseCase(itemID)).thenReturn(Resource.Error(null,responseError = null))
        viewModel.fetchMenuItemDetails(itemID)
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("Unknown Error", (viewModel.menuState.value as MenuUIState.Error).message)
    }
    @Test
    fun `fetchMenuItemDetails return NoDataFoundError`() = runTest(testDispatcher) {
        var itemID = 123
        `when`(menuItemMoreInfoUseCase(itemID)).thenReturn(Resource.Error(null,responseError = ResponseError.NoDataFoundError))
        viewModel.fetchMenuItemDetails(itemID)
        assertTrue(viewModel.menuState.value is MenuUIState.Error)
        assertEquals("No More Data", (viewModel.menuState.value as MenuUIState.Error).message)
    }

    @Test
    fun `updateMenuUIState update Success`()= runTest(testDispatcher) {
        viewModel.updateMenuUIState(MenuUIState.Success)
        assertEquals(MenuUIState.Success, viewModel.menuState.value)
    }

    @Test
    fun `privateMethod test private method`()= runTest(testDispatcher) {
        val method: Method = MenuViewModel::class.java.getDeclaredMethod("privateMethod")
        method.isAccessible = true
        val text = method.invoke(viewModel) as String
        assertEquals("HelloPrivateMethod", text)
    }
    @Test
    fun `testPrivateMethod test private method`()= runTest(testDispatcher) {
        val text = viewModel.testPrivateMethod()
        assertEquals("HelloPrivateMethod", text)
    }
}