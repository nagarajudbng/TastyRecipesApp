package com.dbng.tastyrecipesapp.feature_menu.data.datasource

import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuResponse
import com.dbng.tastyrecipesapp.feature_menu.data.network.MenuApiService
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response


// Created by Nagaraju on 13/11/24.

class MenuRemoteDataSourceTest {

    private lateinit var menuRemoteDataSource: MenuRemoteDataSource
    private lateinit var menuApiService: MenuApiService
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        menuApiService = mock(MenuApiService::class.java)
        menuRemoteDataSource = MenuRemoteDataSource(menuApiService, testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `fetchMenuItems should return list of menu items when API call is successful`() = runTest(testDispatcher) {
        val from = 0
        val size = 0
        val result = Response.success(MenuResponse(count = 2387, results = arrayListOf()))
        `when`(menuApiService.fetchMenuItems(from,size)).thenReturn(result)
        val response = menuRemoteDataSource.fetchMenuItems(from, size)
        assert(response == result)
        verify(menuApiService).fetchMenuItems(from, size)
    }
    @Test
    fun `fetchMenuItems should return error when API call fails`() = runTest(testDispatcher) {
        val from = 0
        val size = 0
        val result = Response.error<MenuResponse>(404, ResponseBody.create(null, ""))
        `when`(menuApiService.fetchMenuItems(from,size)).thenReturn(result)
        val response = menuRemoteDataSource.fetchMenuItems(from, size)
        assert(response == result)
        verify(menuApiService).fetchMenuItems(from, size)
    }

    @Test
    fun `fetchMenuItemMoreInfo should return menu item more info when API call is successful`() = runTest(testDispatcher){
        val itemID = 212
        val result = Response.success(MenuItem(id = itemID, name = "Test", description = "Test"))
        `when`(menuApiService.fetchMenuItemMoreInfo(itemID)).thenReturn(result)
        val response = menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)
        assert(response == result)
        verify(menuApiService).fetchMenuItemMoreInfo(itemID)
    }
    @Test
    fun `fetchMenuItemMoreInfo should return error when API call fails`() = runTest(testDispatcher){
        val itemID = 212
        val result = Response.error<MenuItem>(404, ResponseBody.create(null, ""))
        `when`(menuApiService.fetchMenuItemMoreInfo(itemID)).thenReturn(result)
        val response = menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)
        assert(response == result)
        verify(menuApiService).fetchMenuItemMoreInfo(itemID)
    }
}