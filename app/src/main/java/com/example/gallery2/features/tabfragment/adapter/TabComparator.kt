package com.example.gallery2.features.tabfragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.gallery2.features.tabfragment.TabViewData

class TabComparator : DiffUtil.ItemCallback<TabViewData>() {

    override fun areItemsTheSame(oldItem: TabViewData, newItem: TabViewData): Boolean {
        return oldItem.image == newItem.image
    }

    override fun areContentsTheSame(oldItem: TabViewData, newItem: TabViewData): Boolean {
        return oldItem == newItem
    }
}