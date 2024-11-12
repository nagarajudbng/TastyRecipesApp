package com.dbng.tastyrecipesapp.feature_menu.domain.usecase

import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.domain.repository.MenuRepository
import javax.inject.Inject

class  MenuItemMoreInfoUseCase @Inject constructor(
    private val repository : MenuRepository
) {
    suspend operator fun invoke(itemID:Int): Resource<MenuItem> {
        return repository.fetchMenuItemsMoreInfo(itemID)
    }
}