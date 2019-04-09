package com.bumble.headline.repository

import com.bumble.headline.model.entity.Article

/**
 *  NewsRepository
 *  Scope - Singleton
 *
 *   The news repository class holds an array list with all the news articles
 *
 *   getSelectedNews - Get the selected news based on the index provided
 *   addNews - Add new news feed into the ArrayList
 *   clearNewsList -  Clear the news feed present on the ArrayList
 *
 */

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