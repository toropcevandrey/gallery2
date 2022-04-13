package com.example.gallery2.features.profile.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ProfileAdapter(
    private val onClickListener: OnPhotoClickListener
) : ListAdapter<ProfileViewData, ProfileViewHolder>(ProfileComparator()) {

    interface OnPhotoClickListener {
        fun onPhotoClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder.create(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id.toString(), current.image)
    }
}