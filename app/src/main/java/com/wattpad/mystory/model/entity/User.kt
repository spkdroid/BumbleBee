package com.wattpad.mystory.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("fullname")
    @Expose
    var fullname: String? = null

}