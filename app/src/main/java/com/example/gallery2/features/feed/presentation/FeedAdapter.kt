package com.example.gallery2.features.feed.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gallery2.features.tabfragment.presentation.TabFragment
import com.example.gallery2.utils.Constants.ARG_TABS

class FeedAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_TABS, position)
        }
        return fragment
    }
}