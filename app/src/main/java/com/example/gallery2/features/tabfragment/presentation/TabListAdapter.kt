package com.example.gallery2.features.tabfragment.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class TabListAdapter : ListAdapter<TabViewData, TabViewHolder>(TabComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        return TabViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.image)
    }

}