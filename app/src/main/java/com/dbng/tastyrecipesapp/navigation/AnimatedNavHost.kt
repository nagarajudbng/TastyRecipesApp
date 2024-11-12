package com.dbng.tastyrecipesapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dbng.tastyrecipesapp.feature_menu.presentation.menu.ui.MenuScreen
import com.dbng.tastyrecipesapp.feature_menu.presentation.menudetails.MenuDetailsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch


// Created by Nagaraju on 13/11/24.

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyAnimatedNavHost(modifier: Modifier = Modifier,
                    navController: NavHostController,
                    startDestination: String = NavigationItem.Menu.route
) {
    val navController = rememberAnimatedNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column (
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding()
            )
            ){
            AnimatedNavHost(
                navController = navController,
                startDestination = NavigationItem.Menu.route
            ) {
                composable(
                    route = NavigationItem.Menu.route,
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
                    }
                ) {
                    MenuScreen(
                        navController,
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
                    arguments = listOf(navArgument("itemID"){type= NavType.IntType}),
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
                    },
                    popEnterTransition = {
                        slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
                    },
                    popExitTransition = {
                        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
                    }
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