package com.bumble.headline

import com.bumble.headline.model.entity.Article


object NewsRepository  {


     var newsList:ArrayList<Article> = ArrayList()

    fun initializeRepository(articleList:ArrayList<Article>) {
        newsList.addAll(articleList)
    }

     fun getAll(): ArrayList<Article> {
        return newsList
    }

    fun getSelectedNews(index:Int):Article{
        return newsList[index]
    }

     fun addNews(article: Article) {
        newsList.add(article)
    }

    fun clearNewsList() {
        newsList.clear()
    }


}