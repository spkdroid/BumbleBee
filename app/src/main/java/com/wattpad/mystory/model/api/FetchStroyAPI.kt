package com.wattpad.mystory.model.api

import com.wattpad.mystory.model.entity.ArticleCollection
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FetchStroyAPI {
    @GET("v2/top-headlines")
    fun loadStory(@QueryMap options:Map<String, String>): Observable<ArticleCollection>
}