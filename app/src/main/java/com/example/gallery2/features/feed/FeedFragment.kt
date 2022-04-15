package com.example.gallery2.features.feed

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gallery2.databinding.FragmentFeedBinding
import com.example.gallery2.views.DelayTextWatcher
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class FeedFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentFeedBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            pager.adapter = FeedAdapter(this@FeedFragment)
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = TAB_NAMES[position]
            }.attach()

            fabAddFile.setOnClickListener {
                findNavController().navigate(
                    FeedFragmentDirections.navigateFeedFragmentToBottomFragment()
                )
            }

            etSearch.addTextChangedListener(DelayTextWatcher { query ->
                sharedViewModel.sendSearchData(query)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_NAMES = arrayOf("New", "Popular")
    }
}