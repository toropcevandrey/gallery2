package com.example.gallery2.features.profile.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.R
import com.example.gallery2.utils.Constants

class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val iv: ImageView = itemView.findViewById(R.id.iv_photo)

    fun bind(image: String) {

        Glide.with(itemView)
            .load(Constants.BASE_URL_MEDIA + image)
            .placeholder(R.drawable.progress_animation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(iv)
    }

    companion object {
        fun create(parent: ViewGroup): ProfileViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_tab, parent, false)
            return ProfileViewHolder(view)
        }
    }
}