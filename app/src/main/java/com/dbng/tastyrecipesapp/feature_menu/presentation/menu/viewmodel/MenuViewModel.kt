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
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.GetTotalItemCountUseCase
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.MenuItemMoreInfoUseCase
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.MenuUseCase
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.utils.MenuUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuUseCase: MenuUseCase,
    private val getTotalItemCountUseCase: GetTotalItemCountUseCase,
    private val menuItemMoreInfoUseCase: MenuItemMoreInfoUseCase
) : ViewModel() {

    private val _menuState = mutableStateOf<MenuUIState>(MenuUIState.Loading)
    val menuState = _menuState
    private val _items = mutableStateOf<PersistentList<MenuItem>>(emptyList<MenuItem>().toPersistentList())
    val items = _items
    private val _detailItems = mutableStateOf<MenuItem?>(null)
    val detailItems = _detailItems
    var isLoading = mutableStateOf(false)

    private var currentIndex = 0
    private var pageSize = 20
    private var totalItemsCount = 0
    fun fetchMenuList() {
        viewModelScope.launch {
            if(totalItemsCount> 0 && _items.value.size>=totalItemsCount) return@launch
            isLoading.value = true
            try {
                when (val response = menuUseCase(currentIndex, pageSize)) {
                    is Resource.Success -> {
                        val newItems = response.data ?: emptyList()
                        _items.value = newItems.toPersistentList()
                        _menuState.value = MenuUIState.Success

                        // Update indices and counts for pagination
                        currentIndex = _items.value.size
                        totalItemsCount = getTotalItemCountUseCase()
                    }
                    is Resource.Error -> {
                        val errorMessage = when (response.responseError) {
                            ResponseError.NetworkError -> "Network Error"
                            ResponseError.ServerError -> "Server Error"
                            ResponseError.UnknownError -> "Unknown Error"
                            null -> "Unknown Error"
                        }
                        _menuState.value = MenuUIState.Error(errorMessage)
                    }
                }
            } finally {
                isLoading.value = false
            }
        }
    }
    fun fetchMenuItemDetails(itemID: Int) {
        viewModelScope.launch {
            when (val response = menuItemMoreInfoUseCase(itemID)) {
                is Resource.Success -> {
                    response.data?.let { item ->
                        _detailItems.value = response.data
                        _menuState.value = MenuUIState.Success
                    }
                }
                is Resource.Error -> {
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