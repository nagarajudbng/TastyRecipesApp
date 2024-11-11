package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Measurements (

  @SerializedName("id"       ) var id       : Int?    = null,
  @SerializedName("quantity" ) var quantity : String? = null,
  @SerializedName("unit"     ) var unit     : Unit?   = Unit()

)