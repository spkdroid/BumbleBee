package com.wattpad.mystory.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Book {

    @SerializedName("stories")
    @Expose
    var stories: List<Story>? = null

}