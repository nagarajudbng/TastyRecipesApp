package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class TotalTimeTier (

  @SerializedName("display_tier" ) var displayTier : String? = null,
  @SerializedName("tier"         ) var tier        : String? = null

)