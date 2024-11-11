package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Components (

  @SerializedName("extra_comment" ) var extraComment : String?                 = null,
  @SerializedName("id"            ) var id           : Int?                    = null,
  @SerializedName("ingredient"    ) var ingredient   : Ingredient?             = Ingredient(),
  @SerializedName("measurements"  ) var measurements : ArrayList<Measurements> = arrayListOf(),
  @SerializedName("position"      ) var position     : Int?                    = null,
  @SerializedName("raw_text"      ) var rawText      : String?                 = null

)