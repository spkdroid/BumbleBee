package com.wattpad.mystory.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.Navigation
import com.wattpad.mystory.R
import com.wattpad.mystory.di.component.DaggerNetworkComponent
import com.wattpad.mystory.model.api.FetchStroyAPI
import com.wattpad.mystory.util.Constants
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
        var storyList = ArrayList<Story>()
        val compositeDisposable = CompositeDisposable()
        var searchResult: ArrayList<Story> = ArrayList()
    }

    init {
        DaggerNetworkComponent.builder().build().inject(this)
    }

    @Inject
    lateinit var fetchStory: Retrofit

    var bookList: ArrayList<Story> = ArrayList()

    private var searchEnabled: Boolean = false

    fun getBooks(
        context: Context,
        BookList: RecyclerView,
        loadProgressBar: ProgressBar
    ): ArrayList<Story> {

        if (!NetworkStatus().isDataAvailable(context)) {
            DialogBuilder().progressDialog(context, "Network Alert", "No Data or Wifi available on the device").show()
            loadProgressBar.visibility = View.GONE
            return storyList
        }

        val mService = fetchStory.create(FetchStroyAPI::class.java)

        storyList.clear()

        val disposable: Disposable = mService.loadStory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        result.stories!!.forEach {
                            storyList.add(it)
                        }
                    }
                    BookList.adapter!!.notifyDataSetChanged()
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


    fun clear() {
        storyList.clear()
    }

    fun navigateSelectedItem(ctx: View, position: Int) {
        val bundle = Bundle()

        if (searchEnabled) {
            bundle.putString(Constants.bookUrl, searchResult[position].cover)
            bundle.putString(Constants.bookName, searchResult[position].title)
            bundle.putString(Constants.userUrl, searchResult[position].user?.avatar)
            bundle.putString(Constants.userName, searchResult[position].user?.name)
            bundle.putString(Constants.userFullName, searchResult[position].user?.fullname)
        } else {
            bundle.putString(Constants.bookUrl, storyList[position].cover)
            bundle.putString(Constants.bookName, storyList[position].title)
            bundle.putString(Constants.userUrl, storyList[position].user?.avatar)
            bundle.putString(Constants.userName, storyList[position].user?.name)
            bundle.putString(Constants.userFullName, storyList[position].user?.fullname)
        }
        Navigation.findNavController(ctx).navigate(R.id.action_mainFragment_to_storyDetailFragment, bundle)
    }

    fun searchStory(searchString: String): ArrayList<Story> {
        return if (searchString.isEmpty()) {
            searchEnabled = false
            storyList
        } else {
            searchEnabled = true
            searchResult.clear()
            searchResult = storyList.filter { story ->
                (story?.title?.startsWith(
                    searchString,
                    true
                )!! || story.user?.fullname?.startsWith(searchString, true)!!)
            } as ArrayList<Story>
            searchResult
        }
    }

    fun isTabletDevice(ctx: Context): Boolean {
        return ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun isLandScapeMode(ctx: Context): Boolean {
        return (ctx.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}