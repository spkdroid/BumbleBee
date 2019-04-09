package com.bumble.headline.viewmodel

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.ProgressBar
import com.bumble.headline.model.api.FetchStroyAPI
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumble.headline.NewsRepository
import com.bumble.headline.di.component.DaggerNetworkComponent
import com.bumble.headline.model.entity.Article
import com.bumble.headline.model.entity.ArticleCollection
import com.bumble.headline.repository.CountryRepository
import com.wattpad.headlines.util.DialogBuilder
import com.wattpad.headlines.util.NetworkStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


class NewsListViewModel : ViewModel() {

    companion object {
        var newsList = ArrayList<Article>()
        val compositeDisposable = CompositeDisposable()
    }

    init {
        DaggerNetworkComponent.builder().build().inject(this)
    }

    @Inject
    lateinit var fetchStory: Retrofit

    var newsList: ArrayList<Article> = ArrayList()

    fun getBooks(
        context: Context,
        newsRecyclerView: RecyclerView,
        loadProgressBar: ProgressBar
    ): ArrayList<Article> {

        if (!NetworkStatus().isDataAvailable(context)) {
            DialogBuilder().progressDialog(context, "Network Alert", "No Data or Wifi available on the device").show()
            loadProgressBar.visibility = View.GONE
            return newsList
        }

        val mService = fetchStory.create(FetchStroyAPI::class.java)

        newsList.clear()

        val data = HashMap<String, String>()
        data["country"] = CountryRepository.getSelectedCountryCode()
        data["apiKey"] = "ee5eaccd9e8a451089e664ab00b1b1db"

        val disposable: Disposable = mService.loadStory(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> showResult(result, newsRecyclerView)
                },
                {
                    showError(loadProgressBar)
                }, {
                    showComplete(loadProgressBar,newsRecyclerView)
                }
            )
        compositeDisposable.add(disposable)
        return newsList
    }

    private fun showComplete(loadProgressBar: ProgressBar, newsRecyclerView: RecyclerView) {
        loadProgressBar.visibility = View.GONE
        newsRecyclerView.visibility = View.VISIBLE
    }

    private fun showError(loadProgressBar: ProgressBar) {
        loadProgressBar.visibility = View.GONE
    }

    private fun showResult(result: ArticleCollection?, newsRecyclerView: RecyclerView) {

        result!!.articles!!.forEach {
            newsList.add(it)
            NewsRepository.addNews(it)
          }
        newsRecyclerView.adapter!!.notifyDataSetChanged()
    }

    fun clear() {
        newsList.clear()
        NewsRepository.clearNewsList()
    }

    fun isTabletDevice(ctx: Context): Boolean {
        return ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun isLandScapeMode(ctx: Context): Boolean {
        return (ctx.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}