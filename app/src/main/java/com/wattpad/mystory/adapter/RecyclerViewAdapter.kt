package com.wattpad.mystory.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wattpad.mystory.R
import com.wattpad.mystory.model.entity.Article


class RecyclerViewAdapter(private val mContext: Context, private val mData: List<Article>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

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


        Glide.with(mContext).load(mData[position].urlToImage).into(holder.bookImage)

        if (mData[position].title != null)
            holder.newsTitleText.text = mData[position].title
        else {
            holder.newsTitleText.text = ""
        }
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    internal class RecyclerTouchListener(
        context: Context,
        recycleView: androidx.recyclerview.widget.RecyclerView,
        private val clicklistener: ClickListener?
    ) : androidx.recyclerview.widget.RecyclerView.OnItemTouchListener {
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

        override fun onInterceptTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent) {

        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        internal var bookImage: ImageView = itemView.findViewById(R.id.newsGridBackground) as ImageView
        internal var newsTitleText: TextView = itemView.findViewById(R.id.newsTitleText) as TextView
    }
}