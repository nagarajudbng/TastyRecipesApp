package com.dbng.tastyrecipesapp.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
                composable(NavigationItem.Menu.route, enterTransition = {
                    return@composable fadeIn(tween(1000))
                }, exitTransition = {
                    return@composable slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
                    )
                }, popEnterTransition = {
                    return@composable slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                    )
                }) {
                    MenuScreen(
                        navController = navController,
                        onNavigation = { str, id ,name->
                            navController.navigate("${NavigationItem.MenuDetails.route}/${id}/${name}")
                        },
                        showMessage = {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
                composable(
                    "${NavigationItem.MenuDetails.route}/{itemID}/{name}",
                    arguments = listOf(
                        navArgument("itemID"){type= NavType.IntType},
                        navArgument("name"){type= NavType.StringType},
                        ),
                    enterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                        )
                    },
                    popExitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                        )
                    },

                    ) { backStack ->
                    val userType = backStack.arguments?.getInt("itemID") ?: 0
                    val name = backStack.arguments?.getString("name") ?: ""
                    MenuDetailsScreen(
                        navController,
                        name,
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



