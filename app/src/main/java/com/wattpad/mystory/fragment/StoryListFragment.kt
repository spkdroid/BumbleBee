package com.wattpad.mystory.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.wattpad.mystory.R
import com.wattpad.mystory.adapter.RecyclerViewAdapter
import com.wattpad.mystory.model.event.ChangeCountry
import com.wattpad.mystory.viewmodel.StoryListViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class StoryListFragment : androidx.fragment.app.Fragment() {

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
                storyList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            } else {
                storyList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
            }
        } else {
            storyList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)
        }

        storyList.addOnItemTouchListener(
            RecyclerViewAdapter.RecyclerTouchListener(
                this.context!!,
                storyList,
                object :
                    RecyclerViewAdapter.ClickListener {
                    override fun onClick(view: View, position: Int) {
                       // viewModel.navigateSelectedItem(view, position)
                        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_descriptionFragment)
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
    public fun navigateSettings(event: ChangeCountry)
    {
        Navigation.findNavController(this!!.view!!).navigate(R.id.action_mainFragment_to_settingFragment)
    }


}