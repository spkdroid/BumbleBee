package com.bumble.headline

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.bumble.headline.adapter.NewsViewAdapter
import com.bumble.headline.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.michaelbel.bottomsheet.BottomSheet


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

        if (viewModel.bookList.isEmpty()) {
            newsItemList.visibility = View.GONE
            viewModel.bookList = viewModel.getBooks(this.context!!, newsItemList, loadProgressBar)
        } else {
            loadProgressBar.visibility = View.GONE
        }

        newsItemList.apply {
            adapter = NewsViewAdapter(context, viewModel.bookList)
        }

        changeHeadlineLocation.setOnClickListener {
            Navigation.findNavController(this!!.view!!).navigate(R.id.action_newsListFragment_to_settingsFragment)
        }

        swipeContainer.setOnRefreshListener {
            viewModel.clear()
            viewModel.bookList = viewModel.getBooks(this.context!!, newsItemList, loadProgressBar)
            newsItemList.apply {
                adapter = NewsViewAdapter(context, viewModel.bookList)
            }
            newsItemList.adapter?.notifyDataSetChanged()
            swipeContainer.isRefreshing = false
        }

        if (!viewModel.isTabletDevice(this.context!!)) {
            if (viewModel.isLandScapeMode(this.context!!)) {
                newsItemList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            } else {
                newsItemList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            }
        } else {
            newsItemList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
        }

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
                            .setItems(items, icons) { dialog, which ->
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

}
