package com.dbng.tastyrecipesapp.navigation

enum class Screen{
    MENU,
    MENU_DETAILS
}
sealed class NavigationItem(val route:String){

    data object Menu: NavigationItem(Screen.MENU.name)
    data object MenuDetails: NavigationItem(Screen.MENU_DETAILS.name)
}