package com.dbng.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Topics (

  @SerializedName("name" ) var name : String? = null,
  @SerializedName("slug" ) var slug : String? = null

)