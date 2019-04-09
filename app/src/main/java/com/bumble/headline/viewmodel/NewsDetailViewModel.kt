package com.bumble.headline.viewmodel

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.bumble.headline.repository.NewsRepository
import com.bumptech.glide.Glide

class NewsDetailViewModel : ViewModel() {

    fun initializePage(
        newsArticleImage: ImageView,
        newsArticleTime: TextView,
        newsArticleDescription: TextView,
        newsArticleLink: TextView, newsArticleTitle: TextView,
        context: Context
    ) {
        Glide.with(context).load(NewsRepository.selectedArticle.urlToImage).into(newsArticleImage)

        newsArticleTime.text = NewsRepository.selectedArticle.publishedAt

        newsArticleDescription.text = NewsRepository.selectedArticle.content

        newsArticleLink.text = NewsRepository.selectedArticle.url

        newsArticleTitle.text = NewsRepository.selectedArticle.title
    }

}
