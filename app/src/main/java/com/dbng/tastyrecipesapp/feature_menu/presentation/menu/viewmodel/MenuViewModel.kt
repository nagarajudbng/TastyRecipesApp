package com.dbng.tastyrecipesapp.feature_menu.presentation.menu.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.MenuUseCase
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.utils.MenuUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuUseCase: MenuUseCase,
) : ViewModel() {

    private val _menuState = mutableStateOf<MenuUIState>(MenuUIState.Loading)
    val menuState = _menuState
    private val _items = mutableStateOf<List<MenuItem>>(emptyList())
    val items = _items
    var isLoading = mutableStateOf(false)

    private var currentIndex = 0
    private var pageSize = 20
    fun fetchMenuList() {
        viewModelScope.launch {
            isLoading.value = true
            when (val response = menuUseCase(currentIndex, pageSize)) {
                is Resource.Success -> {
                    isLoading.value = false
                    response.data?.let {
                        _items.value += it
                        _menuState.value = MenuUIState.Success
                        currentIndex = items.value.size
                    }
                }

                is Resource.Error -> {
                    isLoading.value = false
                    val errorMessage = when (response.responseError) {
                        ResponseError.NetworkError -> "Network Error"
                        ResponseError.ServerError -> "Server Error"
                        ResponseError.UnknownError -> "Unknown Error"
                        null -> "Unknown Error"
                    }
                    _menuState.value = MenuUIState.Error(errorMessage)
                }
            }
        }
    }
}