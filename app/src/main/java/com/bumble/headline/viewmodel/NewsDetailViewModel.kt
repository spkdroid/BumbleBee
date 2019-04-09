package com.bumble.headline.viewmodel

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel;
import com.bumble.headline.NewsRepository
import com.bumptech.glide.Glide

class NewsDetailViewModel : ViewModel() {

    fun initalizePage(
        newsArticleImage: ImageView,
        newsArticleTime: TextView,
        newsArticleDescription: TextView,
        newsArticleLink: TextView,
        context: Context
    ) {

        Glide.with(context).load(NewsRepository.selectedArticle.urlToImage).into(newsArticleImage)


    }
    // TODO: Implement the ViewModel
}
