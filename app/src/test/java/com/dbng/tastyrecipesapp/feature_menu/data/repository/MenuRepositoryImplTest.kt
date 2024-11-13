package com.dbng.tastyrecipesapp.feature_menu.data.repository


import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError
import com.dbng.tastyrecipesapp.feature_menu.data.datasource.MenuRemoteDataSource
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuResponse
import com.dbng.tastyrecipesapp.feature_menu.data.network.MenuApiService
import com.dbng.tastyrecipesapp.feature_menu.domain.repository.MenuRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


// Created by Nagaraju on 13/11/24.

class MenuRepositoryImplTest {

    private lateinit var menuRemoteDataSource: MenuRemoteDataSource
    private lateinit var menuRepository: MenuRepository
    private lateinit var menuApiService: MenuApiService
    private val testDispatcher = TestCoroutineDispatcher()
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        menuApiService = mock(MenuApiService::class.java)
        menuRemoteDataSource = MenuRemoteDataSource(menuApiService, testDispatcher)
        menuRepository = MenuRepositoryImpl(menuRemoteDataSource)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `fetchMenuItems should return list of menu items`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        val menu = arrayListOf(MenuItem(id = 1, name = "Pizza", thumbnailUrl = "url"))
        val result = Response.success(MenuResponse(count = 2387, results = menu))
        `when`(menuRemoteDataSource.fetchMenuItems(from, size)).thenReturn(result)
        val response = menuRepository.fetchMenuItems(from, size)
        assert(response is Resource.Success)
        assertEquals(1, (response as Resource.Success).data?.size)
        assertEquals("Pizza", response.data?.get(0)?.name ?:"test" )
    }

    @Test
    fun `fetchMenuItems should return error message`()= runTest(testDispatcher) {
        val from = 0
        val size = 20
        val result = Response.error<MenuResponse>(400, ResponseBody.create(null, "Something went wrong"))
        `when`(menuRemoteDataSource.fetchMenuItems(from, size)).thenReturn(result)
        val response = menuRepository.fetchMenuItems(from, size)
        assert(response is Resource.Error)
        assertEquals(ResponseError.UnknownError, (response as Resource.Error).responseError)
    }
    @Test
    fun `fetchMenuItems should returns server error when HttpException occurs`()= runTest(testDispatcher) {
        val from = 0
        val size = 20
        `when`(menuRemoteDataSource.fetchMenuItems(from, size)).thenThrow(HttpException::class.java)
        val response = menuRepository.fetchMenuItems(from, size)
        assert(response is Resource.Error)
        assertEquals(ResponseError.ServerError, (response as Resource.Error).responseError)
    }
    @Test
    fun `fetchMenuItems should returns server error when IOException occurs`()= runTest(testDispatcher) {
        val from = 0
        val size = 20
        given(menuRemoteDataSource.fetchMenuItems(from, size)).willAnswer {
            throw IOException("Ooops")
        }
        val response = menuRepository.fetchMenuItems(from, size)
        assert(response is Resource.Error)
        assertEquals(ResponseError.NetworkError, (response as Resource.Error).responseError)
    }

//    --------------------
    @Test
    fun `fetchMenuItemMoreInfo should return list of menu items`() = runTest(testDispatcher) {
        val itemID = 123
        val menu = MenuItem(id = 1, name = "Pizza", thumbnailUrl = "url")
        val result = Response.success(menu)
        `when`(menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)).thenReturn(result)
        val response = menuRepository.fetchMenuItemsMoreInfo(itemID)
        assert(response is Resource.Success)
        assertEquals("Pizza", response.data?.name ?:"test" )
    }

    @Test
    fun `fetchMenuItemMoreInfo should return error message`()= runTest(testDispatcher) {
        val itemID = 123
        val menu = MenuItem(id = 1, name = "Pizza", thumbnailUrl = "url")
        val result = Response.error<MenuItem>(404, mock())
        `when`(menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)).thenReturn(result)
        val response = menuRepository.fetchMenuItemsMoreInfo(itemID)
        assert(response is Resource.Error)
        assertEquals(ResponseError.UnknownError, (response as Resource.Error).responseError)
    }
    @Test
    fun `fetchMenuItemMoreInfo should returns server error when HttpException occurs`()= runTest(testDispatcher) {
        val itemID = 123
        val menu = MenuItem(id = 1, name = "Pizza", thumbnailUrl = "url")
        val result = Response.error<MenuItem>(404, mock())
        `when`(menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)).thenThrow(HttpException::class.java)
        val response = menuRepository.fetchMenuItemsMoreInfo(itemID)
        assert(response is Resource.Error)
        assertEquals(ResponseError.ServerError, (response as Resource.Error).responseError)
    }
    @Test
    fun `fetchMenuItemMoreInfo should returns server error when IOException occurs`()= runTest(testDispatcher) {
        val itemID = 123
        given(menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)).willAnswer {
            throw IOException("Ooops")
        }
        val response = menuRepository.fetchMenuItemsMoreInfo(itemID)
        assert(response is Resource.Error)
        assertEquals(ResponseError.NetworkError, (response as Resource.Error).responseError)
    }
    @Test
    fun `getTotalItemCount returns correct menuItemsCount value`() {
        val expectedCount = 0
        val itemCount = menuRepository.getTotalItemCount()
        assertEquals(expectedCount, itemCount)
    }
    @Test
    fun `fetchMenuItems updates menuItemsCount`() = runTest(testDispatcher) {
        val menu = arrayListOf(MenuItem(id = 1, name = "Pizza", thumbnailUrl = "url"))
        val mockMenuResponse = MenuResponse(count = 25, results = menu)
        whenever(menuRemoteDataSource.fetchMenuItems(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(Response.success(mockMenuResponse))
        menuRepository.fetchMenuItems(from = 0, size = 20)
        assertEquals(25, menuRepository.getTotalItemCount())
    }

    @Test
    fun `fetchMenuItems does not update menuItemsCount on error response`() = runTest(testDispatcher){
        whenever(menuRemoteDataSource.fetchMenuItems(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "")))
        val result = menuRepository.fetchMenuItems(from = 0, size = 20)
        assertEquals(0, menuRepository.getTotalItemCount())
        assert(result is Resource.Error && result.responseError == ResponseError.UnknownError)
    }
}