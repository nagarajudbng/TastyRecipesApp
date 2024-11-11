package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Tags (

  @SerializedName("display_name"    ) var displayName   : String? = null,
  @SerializedName("id"              ) var id            : Int?    = null,
  @SerializedName("name"            ) var name          : String? = null,
  @SerializedName("parent_tag_name" ) var parentTagName : String? = null,
  @SerializedName("root_tag_type"   ) var rootTagType   : String? = null,
  @SerializedName("type"            ) var type          : String? = null

)