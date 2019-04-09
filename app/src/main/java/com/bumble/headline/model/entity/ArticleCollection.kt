package com.bumble.headline.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticleCollection {

    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null
}