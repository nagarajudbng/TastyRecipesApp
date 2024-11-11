package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Instructions (

  @SerializedName("appliance"    ) var appliance   : String? = null,
  @SerializedName("display_text" ) var displayText : String? = null,
  @SerializedName("end_time"     ) var endTime     : Int?    = null,
  @SerializedName("id"           ) var id          : Int?    = null,
  @SerializedName("position"     ) var position    : Int?    = null,
  @SerializedName("start_time"   ) var startTime   : Int?    = null,
  @SerializedName("temperature"  ) var temperature : String? = null

)