package com.dbng.tastyrecipesapp.feature_menu.presentation.menu.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.GetTotalItemCountUseCase
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.MenuItemMoreInfoUseCase
import com.dbng.tastyrecipesapp.feature_menu.domain.usecase.FetchMenuItemsUseCase
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.utils.MenuUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val fetchMenuItemsUseCase: FetchMenuItemsUseCase,
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

    fun fetchMenuList() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = async {  fetchMenuItemsUseCase(currentIndex, pageSize)}.await()
                when (response) {
                    is Resource.Success -> {
                        val newItems = response.data ?: emptyList()
                        _items.value = (_items.value+newItems.toList()).toPersistentList()
                        _menuState.value = MenuUIState.Success
                        currentIndex = _items.value.size
                    }
                    is Resource.Error -> {
                        val errorMessage = when (response.responseError) {
                            ResponseError.NetworkError -> "Network Error"
                            ResponseError.ServerError -> "Server Error"
                            ResponseError.UnknownError -> "Unknown Error"
                            ResponseError.NoDataFoundError -> "No More Data"
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
            _menuState.value = MenuUIState.Loading
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
                        ResponseError.NoDataFoundError -> {
                            "No More Data"
                        }
                        null -> "Unknown Error"
                    }
                    _menuState.value = MenuUIState.Error(errorMessage)
                }
            }
        }
    }

    fun updateMenuUIState(success: MenuUIState.Success) {
        _menuState.value = success
    }
}