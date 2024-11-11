package com.dbng.tastyrecipesapp.feature_menu.data.network

import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuResponse
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Created by Nagaraju on 11/11/24.

interface MenuApiService {

//    "https://tasty.p.rapidapi.com/recipes/list?from=0&size=20"
    @GET("recipes/list")
    suspend fun fetchMenuItems(@Query("from") from: Int,
                               @Query("size") size: Int):Response<MenuResponse>
    @GET("recipes/get-more-info")
    suspend fun fetchMenuItemMoreInfo(@Query("id") id: Int):Response<MenuItem>

}