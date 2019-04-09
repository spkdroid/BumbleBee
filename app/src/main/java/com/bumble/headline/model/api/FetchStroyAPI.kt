package com.bumble.headline.model.api

import com.bumble.headline.model.entity.ArticleCollection
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FetchStoryAPI {
    @GET("v2/top-headlines")
    fun loadStory(@QueryMap options:Map<String, String>): Observable<ArticleCollection>
}