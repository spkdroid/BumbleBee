package com.wattpad.mystory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.wattpad.mystory.R
import com.wattpad.mystory.adapter.NewsViewAdapter
import com.wattpad.mystory.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.greenrobot.eventbus.EventBus


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
            storyList.visibility = View.GONE
            viewModel.bookList = viewModel.getBooks(this.context!!, storyList, loadProgressBar)
        } else {
            loadProgressBar.visibility = View.GONE
        }

        storyList.apply {
            adapter = NewsViewAdapter(context, viewModel.bookList)
        }

        swipeContainer.setOnRefreshListener {
            viewModel.clear()
            viewModel.bookList = viewModel.getBooks(this.context!!, storyList, loadProgressBar)
            storyList.apply {
                adapter = NewsViewAdapter(context, viewModel.bookList)
            }
            storyList.adapter?.notifyDataSetChanged()
            swipeContainer.isRefreshing = false
        }

        if (!viewModel.isTabletDevice(this.context!!)) {
            if (viewModel.isLandScapeMode(this.context!!)) {
                storyList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            } else {
                storyList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            }
        } else {
            storyList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
        }

        storyList.addOnItemTouchListener(
            NewsViewAdapter.RecyclerTouchListener(
                this.context!!,
                object :
                    NewsViewAdapter.ClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })
        )
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}
