package com.bumble.headline.repository

import com.bumble.headline.model.entity.Article


object NewsRepository {


    private var newsList: ArrayList<Article> = ArrayList()

    lateinit var selectedArticle: Article

    fun getSelectedNews(index: Int): Article {
        return newsList[index]
    }

    fun addNews(article: Article) {
        newsList.add(article)
    }

    fun clearNewsList() {
        newsList.clear()
    }


}