package com.dbng.tastyrecipesapp.feature_menu.presentation.menu.utils

import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem


// Created by Nagaraju on 11/11/24.

sealed class MenuUIState {
    data object Loading : MenuUIState()
    data class Success(val data: List<MenuItem>) : MenuUIState()
    data class Error(val message: String) : MenuUIState()
}