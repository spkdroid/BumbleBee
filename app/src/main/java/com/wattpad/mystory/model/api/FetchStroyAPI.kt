package com.wattpad.mystory.model.api

import com.wattpad.mystory.model.entity.Book
import io.reactivex.Observable
import retrofit2.http.GET

interface FetchStroyAPI {
    @GET("api/v3/stories?offset=0&limit=30&fields=stories(id,title,cover,user)")
    fun loadStory(): Observable<Book>
}