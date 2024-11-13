package com.dbng.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class UserRatings (

  @SerializedName("count_negative" ) var countNegative : Int?    = null,
  @SerializedName("count_positive" ) var countPositive : Int?    = null,
  @SerializedName("score"          ) var score         : Double? = null

)