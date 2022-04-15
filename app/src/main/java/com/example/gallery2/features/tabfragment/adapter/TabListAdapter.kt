package com.example.gallery2.features.tabfragment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.gallery2.features.tabfragment.TabViewData

class TabListAdapter(
    private val onClickListener: OnPhotoClickListener
) : ListAdapter<TabViewData, TabViewHolder>(TabComparator()) {

    interface OnPhotoClickListener {
        fun onPhotoClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        return TabViewHolder.create(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id.toString(), current.image)
    }
}