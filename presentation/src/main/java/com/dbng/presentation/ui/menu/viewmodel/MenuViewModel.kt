package com.dbng.presentation.ui.menu.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbng.core.domain.Resource
import com.dbng.core.domain.utils.ResponseError
import com.dbng.domain.model.MenuItem
import com.dbng.domain.usecase.GetTotalItemCountUseCase
import com.dbng.domain.usecase.MenuItemMoreInfoUseCase
import com.dbng.domain.usecase.FetchMenuItemsUseCase
import com.dbng.presentation.ui.menu.utils.MenuUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val fetchMenuItemsUseCase: com.dbng.domain.usecase.FetchMenuItemsUseCase,
    private val getTotalItemCountUseCase: com.dbng.domain.usecase.GetTotalItemCountUseCase,
    private val menuItemMoreInfoUseCase: com.dbng.domain.usecase.MenuItemMoreInfoUseCase
) : ViewModel() {

    private val _menuState = mutableStateOf<MenuUIState>(MenuUIState.Loading)
    val menuState = _menuState
    private val _items = mutableStateOf<List<com.dbng.domain.model.MenuItem>>(emptyList<com.dbng.domain.model.MenuItem>())
    val items = _items
    private val _detailItems = mutableStateOf<com.dbng.domain.model.MenuItem?>(null)
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
                    is com.dbng.core.domain.Resource.Success -> {
                        val newItems = response.data ?: emptyList()
                        _items.value = (_items.value+newItems.toList())
                        _menuState.value = MenuUIState.Success
                        currentIndex = _items.value.size
                    }
                    is com.dbng.core.domain.Resource.Error -> {
                        val errorMessage = when (response.responseError) {
                            com.dbng.core.domain.utils.ResponseError.NetworkError -> "Network Error"
                            com.dbng.core.domain.utils.ResponseError.ServerError -> "Server Error"
                            com.dbng.core.domain.utils.ResponseError.UnknownError -> "Unknown Error"
                            com.dbng.core.domain.utils.ResponseError.NoDataFoundError -> "No More Data"
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
                is com.dbng.core.domain.Resource.Success -> {
                    response.data?.let { item ->
                        _detailItems.value = response.data
                        _menuState.value = MenuUIState.Success
                    }
                }
                is com.dbng.core.domain.Resource.Error -> {
                    val errorMessage = when (response.responseError) {
                        com.dbng.core.domain.utils.ResponseError.NetworkError -> "Network Error"
                        com.dbng.core.domain.utils.ResponseError.ServerError -> "Server Error"
                        com.dbng.core.domain.utils.ResponseError.UnknownError -> "Unknown Error"
                        com.dbng.core.domain.utils.ResponseError.NoDataFoundError -> "No More Data"
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