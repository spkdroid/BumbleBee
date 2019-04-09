package com.bumble.headline.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.bumble.headline.R
import com.bumble.headline.adapter.NewsViewAdapter
import com.bumble.headline.repository.NewsRepository
import com.bumble.headline.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.michaelbel.bottomsheet.BottomSheet

/**
 *
 * NewsListFragment
 *
 * This is the hub fragment of the bumblebee application
 *
 */


class NewsListFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = NewsListFragment()
    }

    private lateinit var viewModel: NewsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return if (view != null) {
            view as View
        } else {
            inflater.inflate(R.layout.main_fragment, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)

        if (viewModel.newsList.isEmpty()) {
            newsItemList.visibility = View.GONE
            viewModel.newsList = viewModel.getNews(this.context!!, newsItemList, loadProgressBar)
        } else {
            loadProgressBar.visibility = View.GONE
        }

        newsItemList.apply {
            adapter = NewsViewAdapter(context, viewModel.newsList)
        }

        changeHeadlineLocation.setOnClickListener {
            Navigation.findNavController(this.view!!).navigate(R.id.action_newsListFragment_to_settingsFragment)
        }


        // Swipe to refresh the screen
        swipeContainer.setOnRefreshListener {
            refreshNewsScreen()
        }

        /**
         *   This is to calculate the number of grids on the news recyclerview
         *
         *   Phone has one per row
         *   tablet has two per row
         *
         */
        if (!viewModel.isTabletDevice(this.context!!)) {
            if (viewModel.isLandScapeMode(this.context!!)) {
                newsItemList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
            } else {
                newsItemList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            }
        } else {
            newsItemList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        }

        /**
         *
         *   News item click listener implementation.
         *
         */
        newsItemList.addOnItemTouchListener(
            NewsViewAdapter.RecyclerTouchListener(
                this.context!!,
                object :
                    NewsViewAdapter.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val items = intArrayOf(R.string.view, R.string.share)

                        val icons = intArrayOf(
                            R.drawable.ic_attachment_black_24dp,
                            R.drawable.ic_share_black_24dp
                        )
                        val bottomSheetMenu = BottomSheet.Builder(view.context)
                        bottomSheetMenu.setTitle("More Options")
                            .setItems(items, icons) { _, which ->
                                if (which == 0) {
                                    NewsRepository.selectedArticle = NewsRepository.getSelectedNews(position)
                                    Navigation.findNavController(view)
                                        .navigate(R.id.action_newsListFragment_to_newsDetailFragment2)
                                } else {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, NewsRepository.getSelectedNews(position).url)
                                        type = "text/plain"
                                    }
                                    startActivity(sendIntent)
                                }
                            }
                        bottomSheetMenu.show()
                    }
                })
        )
    }


    /**
     *
     * Refresh logic for the news list
     *
     */

    private fun refreshNewsScreen() {
        viewModel.clear()
        viewModel.newsList = viewModel.getNews(this.context!!, newsItemList, loadProgressBar)
        newsItemList.apply {
            adapter = NewsViewAdapter(context, viewModel.newsList)
        }
        newsItemList.adapter?.notifyDataSetChanged()
        swipeContainer.isRefreshing = false
    }


}
