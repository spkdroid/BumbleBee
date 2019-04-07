package com.wattpad.mystory.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wattpad.mystory.R
import com.wattpad.mystory.adapter.RecyclerViewAdapter
import com.wattpad.mystory.model.event.SearchMessage
import com.wattpad.mystory.viewmodel.StoryListViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class StoryListFragment : Fragment() {

    companion object {
        fun newInstance() = StoryListFragment()
    }

    private lateinit var viewModel: StoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        //  Navigation.findNavController(this,R.id.mainFragment)

        return if (view != null) {
            view as View
        } else {
            inflater.inflate(R.layout.main_fragment, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StoryListViewModel::class.java)

        if (viewModel.bookList.isEmpty()) {
            storyList.visibility = View.GONE
            viewModel.bookList = viewModel.getBooks(this.context!!, storyList, loadProgressBar)
        } else {
            loadProgressBar.visibility = View.GONE
        }

        storyList.apply {
            adapter = RecyclerViewAdapter(context, viewModel.bookList)
        }

        swipeContainer.setOnRefreshListener {
            viewModel.clear()
            viewModel.bookList = viewModel.getBooks(this.context!!, storyList, loadProgressBar)
            storyList.apply {
                adapter = RecyclerViewAdapter(context, viewModel.bookList)
            }
            storyList.adapter?.notifyDataSetChanged()
            swipeContainer.isRefreshing = false
        }

        if (!viewModel.isTabletDevice(this.context!!)) {
            if (viewModel.isLandScapeMode(this.context!!)) {
                storyList.layoutManager = GridLayoutManager(context, 1)
            } else {
                storyList.layoutManager = GridLayoutManager(context, 1)
            }
        } else {
            storyList.layoutManager = GridLayoutManager(context, 1)
        }

        storyList.addOnItemTouchListener(
            RecyclerViewAdapter.RecyclerTouchListener(
                this.context!!,
                storyList,
                object :
                    RecyclerViewAdapter.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        viewModel.navigateSelectedItem(view, position)
                    }

                    override fun onLongClick(view: View, position: Int) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun messageEventFromService(event: SearchMessage) {
        val searchResult = viewModel.searchStory(event.searchString)
        storyList.apply {
            adapter = RecyclerViewAdapter(context, searchResult)
        }
        storyList.adapter?.notifyDataSetChanged()
    }


}