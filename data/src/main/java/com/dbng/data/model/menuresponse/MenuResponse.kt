package com.dbng.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class MenuResponse (

  @SerializedName("count"   ) var count   : Int?               = null,
  @SerializedName("results" ) var results : ArrayList<MenuItem> = arrayListOf()

)