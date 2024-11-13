package com.dbng.tastyrecipesapp.feature_menu.presentation.menudetails


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dbng.tastyrecipesapp.core.utils.ThemeColors
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.utils.MenuUIState
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.viewmodel.MenuViewModel


// Created by Nagaraju on 13/11/24.

@Preview
@Composable
fun MenuDefaultsScreenPreview() {
    ItemContent(
        item = MenuItem(
            id = 1,
            name = "Sample Item",
            imageURL = "https://example.com/image.jpg",
            description = "This is a sample item with a description.",
            price = 10,
            quantity = 1,
            menuType = "Sample Type",
            category = "Sample Category",
            subCategory = "Sample Subcategory",
            ingredients = "Sample Ingredients",
            itemType = "Sample Item Type"
        ),
        onNavigation = {
            // Handle navigation here
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun MenuDetailsScreen(
    navController: NavHostController,
    title:String,
    id: Int,
    onNavigation: (String) -> Unit,
    showSnackBar: (String) -> Unit
) {
    val viewModel = hiltViewModel<MenuViewModel>()
    LaunchedEffect(true) {
        viewModel.fetchMenuItemDetails(id)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title ,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            color = ThemeColors.textTopBarColor
                        ),
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

        Card(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                Color.White
            )

        ) {
            when(viewModel.menuState.value){
                is MenuUIState.Success -> {
                    val item = viewModel.detailItems.value
                    if (item != null) {
                        ItemContent(item = item, onNavigation = onNavigation)
                    }
                }

                is MenuUIState.Error -> {
                    showSnackBar("Unknown Error")
                }
                MenuUIState.Loading -> {
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
            }
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemContent(
    item:MenuItem,
    onNavigation: (String) -> Unit
){

    Column(

        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = item.imageURL,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(138.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.description ,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Price: $${item.price ?: 0}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Quantity: ${item.quantity }", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Menu Type: ${item.menuType }", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Category: ${item.category }", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Subcategory: ${item.subCategory }", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Ingredients: ${item.ingredients }", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Item Type: ${item.itemType }", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .height(50.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    onNavigation("BACK")
                }
            ) {
                Text("BACK")
            }
        }
    }
}
