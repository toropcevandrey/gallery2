package com.example.gallery2.features.tabfragment.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.R
import com.example.gallery2.utils.Constants.BASE_URL_MEDIA

class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val iv: ImageView = itemView.findViewById(R.id.iv_photo)

    fun bind(image: String) {

        Glide.with(itemView)
            .load(BASE_URL_MEDIA + image)
            .placeholder(R.drawable.progress_animation)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .into(iv)
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): TabViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_tab, parent, false)
            return TabViewHolder(view)
        }
    }
}