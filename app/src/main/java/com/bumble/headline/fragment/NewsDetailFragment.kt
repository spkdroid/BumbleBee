package com.bumble.headline.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumble.headline.R
import com.bumble.headline.viewmodel.NewsDetailViewModel
import kotlinx.android.synthetic.main.news_detail_fragment.*

/**
 *
 * News Detail fragment is to view more information about the selected news
 *
 */

class NewsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailFragment()
    }

    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsDetailViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.initializePage(
            newsArticleImage, newsArticleTime, newsArticleDescription, newsArticleLink, newsArticleTitle,
            this.context!!
        )
    }

}
