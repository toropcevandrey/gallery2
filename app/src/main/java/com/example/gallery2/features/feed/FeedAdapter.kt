package com.example.gallery2.features.feed

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gallery2.features.tabfragment.new_tab.NewTabFragment
import com.example.gallery2.features.tabfragment.popular_tab.PopularTabFragment

class FeedAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            NewTabFragment()
        } else {
            PopularTabFragment()
        }
    }
}