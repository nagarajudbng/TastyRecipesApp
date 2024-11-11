package com.dbng.tastyrecipesapp.core.utils

import androidx.compose.ui.graphics.Color


// Created by Nagaraju on 23/05/24.

object ThemeColors {
    var dueDateColor = Color(0xFF9FCA6D)
    val statusBarColor = Color(0xFFFFFFFF)
    val textColor = Color(0xFF000000)
    val textTopBarColor = Color(0xFFFFFFFF)
    val buttonsBackgroundColor = Color(0xFF396803)
}
object SearchBarTheme{
    val searchTextColor = Color(0xFF000000)
    val searchBarColor = ThemeColors.statusBarColor
}
object AppBarTheme{
    val appBarColor = ThemeColors.statusBarColor
    val appBarTextColor = Color(0xFF000000)

}