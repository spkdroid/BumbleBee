package com.wattpad.headlines.repository

import com.wattpad.headlines.model.entity.Article
import com.wattpad.headlines.service.RepositoryService

object NewsRepository : RepositoryService {

    lateinit var newsList:ArrayList<Article>

    override fun initializeRepository() {
        newsList = ArrayList()
    }

    override fun getAll(): ArrayList<Article> {
        return newsList
    }

    override fun addNews(article: Article) {
        newsList.add(article)
    }

}