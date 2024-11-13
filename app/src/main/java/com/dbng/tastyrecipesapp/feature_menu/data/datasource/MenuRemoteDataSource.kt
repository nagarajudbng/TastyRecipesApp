package com.dbng.tastyrecipesapp.feature_menu.data.datasource

import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuResponse
import com.dbng.tastyrecipesapp.feature_menu.data.network.MenuApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


// Created by Nagaraju on 11/11/24.

class MenuRemoteDataSource @Inject constructor(
    private val menuApiService: MenuApiService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchMenuItems(from: Int, size: Int): Response<MenuResponse> =
        withContext(ioDispatcher) {
            menuApiService.fetchMenuItems(from,size)
        }

    suspend fun fetchMenuItemMoreInfo(itemID:Int): Response<MenuItem> =
        withContext(ioDispatcher) {
            menuApiService.fetchMenuItemMoreInfo(itemID)
        }
}