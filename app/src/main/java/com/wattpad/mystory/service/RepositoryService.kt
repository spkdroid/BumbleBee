package com.wattpad.mystory.service

import com.wattpad.mystory.model.entity.Article

interface RepositoryService {

    fun initializeRepository()

    fun getAll():ArrayList<Article>

    fun addNews(article:Article)

}