package com.example.gallery2.features.profile.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ProfileAdapter : ListAdapter<String, ProfileViewHolder>(ProfileComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}