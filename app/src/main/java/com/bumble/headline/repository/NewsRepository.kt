package com.bumble.headline

import com.wattpad.headlines.model.entity.Article
import com.wattpad.headlines.service.RepositoryService


object NewsRepository : RepositoryService {

    lateinit var newsList:ArrayList<Article>

    override fun initializeRepository(articleList:ArrayList<Article>) {
        newsList.addAll(articleList)
    }

    override fun getAll(): ArrayList<Article> {
        return newsList
    }

    override fun addNews(article: Article) {
        newsList.add(article)
    }

}