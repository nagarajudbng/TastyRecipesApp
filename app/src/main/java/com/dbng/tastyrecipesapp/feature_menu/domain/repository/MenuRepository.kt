package com.dbng.tastyrecipesapp.feature_menu.domain.repository

import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem

interface MenuRepository {
    suspend fun fetchMenuItems(from: Int, size: Int): Resource<List<MenuItem>>
    fun getTotalItemCount(): Int
    suspend fun fetchMenuItemsMoreInfo(itemID: Int): Resource<MenuItem>
}