package com.wattpad.mystory.viewmodel

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wattpad.mystory.di.component.DaggerNetworkComponent
import com.wattpad.mystory.model.api.FetchStroyAPI
import com.wattpad.mystory.model.entity.Article
import com.wattpad.mystory.model.entity.ArticleCollection
import com.wattpad.mystory.util.DialogBuilder
import com.wattpad.mystory.util.NetworkStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


class StoryListViewModel : ViewModel() {

    companion object {
        var storyList = ArrayList<Article>()
        val compositeDisposable = CompositeDisposable()
    }

    init {
        DaggerNetworkComponent.builder().build().inject(this)
    }

    @Inject
    lateinit var fetchStory: Retrofit

    var bookList: ArrayList<Article> = ArrayList()

    fun getBooks(
        context: Context,
        BookList: androidx.recyclerview.widget.RecyclerView,
        loadProgressBar: ProgressBar
    ): ArrayList<Article> {

        if (!NetworkStatus().isDataAvailable(context)) {
            DialogBuilder().progressDialog(context, "Network Alert", "No Data or Wifi available on the device").show()
            loadProgressBar.visibility = View.GONE
            return storyList
        }

        val mService = fetchStory.create(FetchStroyAPI::class.java)

        storyList.clear()

        val data = HashMap<String, String>()
        data["country"] = "in"
        data["apiKey"] = "ee5eaccd9e8a451089e664ab00b1b1db"


        val disposable: Disposable = mService.loadStory(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> showResult(result, BookList)
                },
                {
                    run {
                        loadProgressBar.visibility = View.GONE
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }, {
                    run {
                        loadProgressBar.visibility = View.GONE
                        BookList.visibility = View.VISIBLE
                        Toast.makeText(context, "Complete", Toast.LENGTH_LONG).show()
                    }
                }
            )
        compositeDisposable.add(disposable)
        return storyList
    }

    private fun showResult(result: ArticleCollection?, BookList: RecyclerView) {

        result!!.articles!!.forEach {
            storyList.add(it)
        }
        BookList.adapter!!.notifyDataSetChanged()
    }

    fun clear() {
        storyList.clear()
    }

    fun isTabletDevice(ctx: Context): Boolean {
        return ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun isLandScapeMode(ctx: Context): Boolean {
        return (ctx.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}