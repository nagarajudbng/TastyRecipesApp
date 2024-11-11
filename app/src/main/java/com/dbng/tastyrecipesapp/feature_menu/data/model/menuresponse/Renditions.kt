package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Renditions (

  @SerializedName("aspect"           ) var aspect         : String? = null,
  @SerializedName("bit_rate"         ) var bitRate        : Int?    = null,
  @SerializedName("container"        ) var container      : String? = null,
  @SerializedName("content_type"     ) var contentType    : String? = null,
  @SerializedName("duration"         ) var duration       : Int?    = null,
  @SerializedName("file_size"        ) var fileSize       : Int?    = null,
  @SerializedName("height"           ) var height         : Int?    = null,
  @SerializedName("maximum_bit_rate" ) var maximumBitRate : String? = null,
  @SerializedName("minimum_bit_rate" ) var minimumBitRate : String? = null,
  @SerializedName("name"             ) var name           : String? = null,
  @SerializedName("poster_url"       ) var posterUrl      : String? = null,
  @SerializedName("url"              ) var url            : String? = null,
  @SerializedName("width"            ) var width          : Int?    = null

)