package com.wattpad.mystory.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Story {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("user")
    @Expose
    var user: User? = null
    @SerializedName("cover")
    @Expose
    var cover: String? = null

}