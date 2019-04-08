package com.wattpad.mystory.repository

import com.wattpad.mystory.model.entity.Article
import com.wattpad.mystory.service.RepositoryService

object NewsRepository : RepositoryService {

    lateinit var newsList:ArrayList<Article>

    override fun initializeRepository() {
        newsList = ArrayList()
    }

    override fun getAll(): ArrayList<Article> {
        return newsList;
    }

    override fun addNews(article: Article) {
        newsList.add(article)
    }

}