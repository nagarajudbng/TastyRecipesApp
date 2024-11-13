package com.dbng.tastyrecipesapp.feature_menu.presentation.menu.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dbng.tastyrecipesapp.R
import com.dbng.tastyrecipesapp.core.utils.ThemeColors
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.utils.MenuUIState
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.viewmodel.MenuViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import androidx.compose.animation.*

@Preview
@Composable
fun MenuScreenPreview() {

}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuScreen(
    navController: NavHostController,
    onNavigation: (String, String,String) -> Unit,
    showMessage: (String) -> Unit
) {
    val viewModel = hiltViewModel<MenuViewModel>()

    LaunchedEffect(Unit) {
        viewModel.fetchMenuList()
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    fun loadMoreItems() {
        coroutineScope.launch {
            if (!viewModel.isLoading.value) {
                viewModel.fetchMenuList()
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Tasty Recipes",
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(color = ThemeColors.textTopBarColor),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.list_background))
                ) {
                    ProductList(
                        viewModel = viewModel,
                        loadMoreItems = ::loadMoreItems,
                        listState = listState,
                        isLoading = viewModel.isLoading.value,
                        onEdit = {},
                        onItemClick = { id,name ->
                            onNavigation("Details", id,name)
                        },
                        showMessage = {
                            showMessage(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductList(
    viewModel: MenuViewModel,
    onEdit: (String) -> Unit,
    onItemClick: (String,String) -> Unit,
    loadMoreItems: () -> Unit,
    listState: LazyListState,
    isLoading: Boolean,
    buffer: Int = 2,
    showMessage: (String) -> Unit,
) {
    val uiState by viewModel.menuState
    val list = viewModel.items.value
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= (totalItemsCount - buffer) && !isLoading
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                loadMoreItems()
            }
    }

    if (uiState is MenuUIState.Loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
    }
    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.list_background))
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(6.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
//        itemsIndexed(viewModel.items.value, key = { _, item -> item.id }) { _, item ->
//            FoodItem(
//                item,
//                onEdit = { onEdit(it) },
//                onItemClick = { id,name->
//                    onItemClick(id,name)
//                }
//            )
//        }
        items(list.size) { item ->
            FoodItem(
                list.get(item),
                onEdit = { onEdit(it) },
                onItemClick = { id,name->
                    onItemClick(id,name)
                }
            )
        }
        when (uiState) {
            is MenuUIState.Loading -> {

            }
            is MenuUIState.Success -> {
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            is MenuUIState.Error -> {
                showMessage(((uiState as MenuUIState.Error).message))
                viewModel.updateMenuUIState(MenuUIState.Success)
            }
        }

    }
}