package com.wattpad.headlines.service

import com.wattpad.headlines.model.entity.Article

interface RepositoryService {

    fun initializeRepository()

    fun getAll():ArrayList<Article>

    fun addNews(article: Article)

}