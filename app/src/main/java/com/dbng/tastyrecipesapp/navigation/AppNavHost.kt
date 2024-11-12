package com.dbng.tastyrecipesapp.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.ui.MenuScreen
import com.dbng.tastyrecipesapp.feature_menu.presentation.menudetails.MenuDetailsScreen
import kotlinx.coroutines.launch

enum class MenuType{
    EDIT,
    ADD
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Menu.route
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Log.d("Navigator","Navigator AppNavHost")
    var context = LocalContext.current;

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
        }
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = startDestination
            ) {
                composable(NavigationItem.Menu.route) {
                    MenuScreen(
                        navController = navController,
                        onNavigation = { str, id ->
                            navController.navigate("${NavigationItem.MenuDetails.route}/${id}")
                        },
                        showMessage = {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
                composable(
                    "${NavigationItem.MenuDetails.route}/{itemID}",
                    arguments = listOf(navArgument("itemID"){type= NavType.IntType})
                ) { backStack ->
                    val userType = backStack.arguments?.getInt("itemID") ?: 0
                    MenuDetailsScreen(
                        navController,
                        userType,
                        onNavigation = {
                            navController.popBackStack()
                        },
                        showSnackBar = { message ->
                            scope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        }
                    )
                }

            }

        }
    }
}



