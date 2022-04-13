package com.example.gallery2.features.profile.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.R
import com.example.gallery2.utils.Constants

class ProfileViewHolder(
    itemView: View,
    private val onPhotoClickListener: ProfileAdapter.OnPhotoClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val iv: ImageView = itemView.findViewById(R.id.iv_photo)
    private val circularProgressDrawable = CircularProgressDrawable(iv.context)

    fun bind(id: String, image: String) {
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(itemView)
            .load(Constants.BASE_URL_MEDIA + image)
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(iv)

        iv.setOnClickListener {
            onPhotoClickListener.onPhotoClick(id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onPhotoClickListener: ProfileAdapter.OnPhotoClickListener
        ): ProfileViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_tab, parent, false)
            return ProfileViewHolder(view, onPhotoClickListener)
        }
    }
}