package com.wattpad.mystory.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mikhaellopez.circularimageview.CircularImageView
import com.wattpad.mystory.R
import com.wattpad.mystory.model.entity.Article

class RecyclerViewAdapter(private val mContext: Context, private val mData: List<Article>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardveiw_item_book, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (mData[position].title != null) {
            if (mData[position].title?.length!! > 20) {
                holder.bookTitle.text = mData[position].title?.substring(0, 20).toString() + "..."
            } else
                holder.bookTitle.text = mData[position].title
        } else {
            holder.bookTitle.text = ""
        }

        val options = RequestOptions()

        Glide.with(mContext).load(mData[position].urlToImage).into(holder.bookImage)
            .apply {
                options.centerCrop()
                options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                options.dontTransform()
                options.onlyRetrieveFromCache(true)
            }

       /* Glide.with(mContext).load(mData[position].user?.avatar).into(holder.userImage).apply {
            options.centerCrop()
            options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            options.dontTransform()
            options.onlyRetrieveFromCache(true)
        } */

        if (mData[position].author != null)
            holder.userText.text = mData[position].author
        else {
            holder.userText.text = ""
        }
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    internal class RecyclerTouchListener(
        context: Context,
        recycleView: RecyclerView,
        private val clicklistener: ClickListener?
    ) : RecyclerView.OnItemTouchListener {
        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recycleView.findChildViewUnder(e.x, e.y)
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var bookTitle: TextView = itemView.findViewById(R.id.bookTitleName)
        internal var bookImage: ImageView = itemView.findViewById(R.id.bookImageUrl) as ImageView
        internal var userImage: CircularImageView =
            itemView.findViewById(R.id.userProfileImage) as CircularImageView
        internal var userText: TextView = itemView.findViewById(R.id.userNameText)
    }
}