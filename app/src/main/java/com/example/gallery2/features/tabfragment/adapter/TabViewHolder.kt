package com.example.gallery2.features.tabfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.R
import com.example.gallery2.utils.Constants.BASE_URL_MEDIA

class TabViewHolder(
    itemView: View,
    private val onClickListener: TabListAdapter.OnPhotoClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val iv: ImageView = itemView.findViewById(R.id.iv_photo)
    private val circularProgressDrawable = CircularProgressDrawable(iv.context).apply {
        strokeWidth = 5f
        centerRadius = 50f
        start()
    }

    fun bind(id: String, image: String) {
        Glide.with(itemView)
            .load(BASE_URL_MEDIA + image)
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .centerCrop()
            .into(iv)

        iv.setOnClickListener {
            onClickListener.onPhotoClick(id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickListener: TabListAdapter.OnPhotoClickListener
        ): TabViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_tab, parent, false)
            return TabViewHolder(view, onClickListener)
        }
    }
}