package com.example.gallery2.features.tabfragment.presentation

import androidx.recyclerview.widget.DiffUtil

class TabComparator : DiffUtil.ItemCallback<TabViewData>() {

    override fun areItemsTheSame(oldItem: TabViewData, newItem: TabViewData): Boolean {
        return oldItem.image == newItem.image
    }

    override fun areContentsTheSame(oldItem: TabViewData, newItem: TabViewData): Boolean {
        return oldItem == newItem
    }
}