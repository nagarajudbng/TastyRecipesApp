package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Show (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)