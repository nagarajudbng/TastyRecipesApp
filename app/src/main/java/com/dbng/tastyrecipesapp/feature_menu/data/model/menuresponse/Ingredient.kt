package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Ingredient (

  @SerializedName("created_at"       ) var createdAt       : Int?    = null,
  @SerializedName("display_plural"   ) var displayPlural   : String? = null,
  @SerializedName("display_singular" ) var displaySingular : String? = null,
  @SerializedName("id"               ) var id              : Int?    = null,
  @SerializedName("name"             ) var name            : String? = null,
  @SerializedName("updated_at"       ) var updatedAt       : Int?    = null

)