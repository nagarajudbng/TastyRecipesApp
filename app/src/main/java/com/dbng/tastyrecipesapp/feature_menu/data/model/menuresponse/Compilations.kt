package com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse

import com.google.gson.annotations.SerializedName


data class Compilations (

  @SerializedName("approved_at"        ) var approvedAt       : Int?              = null,
  @SerializedName("aspect_ratio"       ) var aspectRatio      : String?           = null,
  @SerializedName("beauty_url"         ) var beautyUrl        : String?           = null,
  @SerializedName("buzz_id"            ) var buzzId           : String?           = null,
  @SerializedName("canonical_id"       ) var canonicalId      : String?           = null,
  @SerializedName("country"            ) var country          : String?           = null,
  @SerializedName("created_at"         ) var createdAt        : Int?              = null,
  @SerializedName("description"        ) var description      : String?           = null,
  @SerializedName("draft_status"       ) var draftStatus      : String?           = null,
  @SerializedName("facebook_posts"     ) var facebookPosts    : ArrayList<String> = arrayListOf(),
  @SerializedName("id"                 ) var id               : Int?              = null,
  @SerializedName("is_shoppable"       ) var isShoppable      : Boolean?          = null,
  @SerializedName("keywords"           ) var keywords         : String?           = null,
  @SerializedName("language"           ) var language         : String?           = null,
  @SerializedName("name"               ) var name             : String?           = null,
  @SerializedName("promotion"          ) var promotion        : String?           = null,
  @SerializedName("show"               ) var show             : ArrayList<Show>   = arrayListOf(),
  @SerializedName("slug"               ) var slug             : String?           = null,
  @SerializedName("thumbnail_alt_text" ) var thumbnailAltText : String?           = null,
  @SerializedName("thumbnail_url"      ) var thumbnailUrl     : String?           = null,
  @SerializedName("video_id"           ) var videoId          : Int?              = null,
  @SerializedName("video_url"          ) var videoUrl         : String?           = null

)