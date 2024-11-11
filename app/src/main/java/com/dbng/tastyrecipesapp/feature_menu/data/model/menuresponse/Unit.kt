package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Unit (

  @SerializedName("abbreviation"     ) var abbreviation    : String? = null,
  @SerializedName("display_plural"   ) var displayPlural   : String? = null,
  @SerializedName("display_singular" ) var displaySingular : String? = null,
  @SerializedName("name"             ) var name            : String? = null,
  @SerializedName("system"           ) var system          : String? = null

)