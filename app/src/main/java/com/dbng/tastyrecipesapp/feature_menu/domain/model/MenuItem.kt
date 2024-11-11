package com.dbng.tastyrecipesapp.feature_menu.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class MenuItem(
 val id: Int,
 val name: String,
 val imageURL: String
) : Parcelable