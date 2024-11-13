package com.dbng.tastyrecipesapp.feature_menu.domain.usecase

import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.domain.repository.MenuRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

// Created by Nagaraju on 13/11/24.

class MenuItemMoreInfoUseCaseTest {
    private lateinit var menuRepository: MenuRepository
    private lateinit var menuUseCase: MenuItemMoreInfoUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        menuRepository = Mockito.mock(MenuRepository::class.java)
        menuUseCase = MenuItemMoreInfoUseCase(menuRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `invoke Item More info when repository fetchItemMoreInfo succeeds`() =
        runTest(testDispatcher) {
            val itemID = 123
            val mockMenuItem = MenuItem(
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
                    ingredients = "e")
            whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
                Resource.Success(
                    mockMenuItem
                )
            )
            val useCaseResult = menuUseCase(itemID)
            assert(useCaseResult is Resource.Success)
            Assert.assertEquals(itemID, (useCaseResult as Resource.Success).data?.id)
        }

    @Test
    fun `invoke returns error when repository fetch fails`() = runTest(testDispatcher) {
        val itemID = 123
        whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
            Resource.Error(
                data = null, responseError = ResponseError.UnknownError
            )
        )
        val response = menuUseCase(itemID)
        assert(response is Resource.Error)
        Assert.assertEquals(ResponseError.UnknownError, (response as Resource.Error).responseError)
    }

    @Test
    fun `invoke returns Exception when repository IOException`() = runTest(testDispatcher) {
        val itemID = 123
        whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
            Resource.Error(data = null, responseError = ResponseError.NetworkError)
        )
        val useCaseResult = menuUseCase(itemID)
        assert(useCaseResult is Resource.Error)
        Assert.assertEquals(
            ResponseError.NetworkError,
            (useCaseResult as Resource.Error).responseError
        )
    }

    @Test
    fun `invoke returns Exception when repository HTTPException`() = runTest(testDispatcher) {
        val itemID = 123
        whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
            Resource.Error(data = null, responseError = ResponseError.ServerError)
        )
        val useCaseResult = menuUseCase(itemID)
        assert(useCaseResult is Resource.Error)
        Assert.assertEquals(
            ResponseError.ServerError,
            (useCaseResult as Resource.Error).responseError
        )
    }
}