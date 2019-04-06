package com.wattpad.mystory.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.wattpad.mystory.R
import com.wattpad.mystory.util.Constants
import com.wattpad.mystory.viewmodel.StoryDetailViewModel
import kotlinx.android.synthetic.main.story_detail_fragment.*

class StoryDetailFragment : Fragment() {

    companion object {
        fun newInstance() = StoryDetailFragment()
    }

    private lateinit var viewModel: StoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.story_detail_fragment, container, false)
        setMenuVisibility(false)
        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.search)
        item.isVisible = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StoryDetailViewModel::class.java)
        val bookUrl = arguments?.getString(Constants.bookUrl)
        val bookTitle = arguments?.getString(Constants.bookName)
        val userUrl = arguments?.getString(Constants.userUrl)
        val userName = arguments?.getString(Constants.userName)
        val userFull = arguments?.getString(Constants.userFullName)

        viewModel.setStoryPageDetails(
            context,
            bookUrl,
            bookTitle,
            userUrl,
            userName,
            userFull,
            bookImageView,
            bookDetailTitleText,
            userProfileDetailImage,
            userNameDetailText,
            userFullDetailText
        )
    }


}