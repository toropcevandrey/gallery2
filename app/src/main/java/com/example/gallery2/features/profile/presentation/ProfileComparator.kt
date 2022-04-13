package com.example.gallery2.features.profile.presentation

import androidx.recyclerview.widget.DiffUtil

class ProfileComparator : DiffUtil.ItemCallback<ProfileViewData>() {

    override fun areItemsTheSame(oldItem: ProfileViewData, newItem: ProfileViewData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProfileViewData, newItem: ProfileViewData): Boolean {
        return oldItem == newItem
    }
}