package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Nutrition (

  @SerializedName("calories"      ) var calories      : Int?    = null,
  @SerializedName("carbohydrates" ) var carbohydrates : Int?    = null,
  @SerializedName("fat"           ) var fat           : Int?    = null,
  @SerializedName("fiber"         ) var fiber         : Int?    = null,
  @SerializedName("protein"       ) var protein       : Int?    = null,
  @SerializedName("sugar"         ) var sugar         : Int?    = null,
  @SerializedName("updated_at"    ) var updatedAt     : String? = null

)