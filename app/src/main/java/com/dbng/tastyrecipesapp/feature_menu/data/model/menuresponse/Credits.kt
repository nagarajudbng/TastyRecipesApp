package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Credits (

  @SerializedName("is_verified" ) var isVerified : Boolean? = null,
  @SerializedName("name"        ) var name       : String?  = null,
  @SerializedName("picture_url" ) var pictureUrl : String?  = null,
  @SerializedName("type"        ) var type       : String?  = null,
  @SerializedName("user_id"     ) var userId     : String?  = null

)