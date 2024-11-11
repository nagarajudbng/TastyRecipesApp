package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Sections (

  @SerializedName("components" ) var components : ArrayList<Components> = arrayListOf(),
  @SerializedName("name"       ) var name       : String?               = null,
  @SerializedName("position"   ) var position   : Int?                  = null

)