package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Brand (

  @SerializedName("id"      ) var calories      : Int?    = null,
  @SerializedName("image_url" ) var image_url : String?    = null,
  @SerializedName("name"           ) var name           : String?    = null,
  @SerializedName("slug"         ) var slug         : String?    = null,

)