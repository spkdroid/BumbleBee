package com.wattpad.mystory.viewmodel

import android.arch.lifecycle.ViewModel;
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView

class StoryDetailViewModel : ViewModel() {

    fun setStoryPageDetails(
        context: Context?,
        bookUrl: String?,
        bookTitle: String?,
        userUrl: String?,
        userName: String?,
        userFull: String?,
        bookImageView: ImageView?,
        bookDetailTitleText: TextView?,
        userProfileDetailImage: CircularImageView?,
        userNameDetailText: TextView?,
        userFullDetailText: TextView?
    ) {
        if (bookImageView != null && context != null) {
            Glide.with(context).load(bookUrl).into(bookImageView)
        }

        if (bookDetailTitleText != null) {
            bookDetailTitleText.text = bookTitle
        }

        if (userProfileDetailImage != null && context != null) {
            Glide.with(context).load(userUrl).into(userProfileDetailImage)
        }

        if (userNameDetailText != null) {
            userNameDetailText.text = userName
        }

        if (userFullDetailText != null) {
            userFullDetailText.text = userFull
        }
    }
}
