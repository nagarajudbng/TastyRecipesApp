package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Price (

  @SerializedName("consumption_portion" ) var consumptionPortion : Int?    = null,
  @SerializedName("consumption_total"   ) var consumptionTotal   : Int?    = null,
  @SerializedName("portion"             ) var portion            : Int?    = null,
  @SerializedName("total"               ) var total              : Int?    = null,
  @SerializedName("updated_at"          ) var updatedAt          : String? = null

)