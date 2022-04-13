package com.example.gallery2.features.feed.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gallery2.databinding.FragmentFeedBinding
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment : Fragment() {

    private lateinit var adapter: FeedAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        initViews()

        return binding.root
    }

    private fun initViews() {
        adapter = FeedAdapter(this)
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = TAB_NAMES[position]
        }.attach()

        binding.fabAddFile.setOnClickListener {
            findNavController().navigate(
                FeedFragmentDirections.navigateFeedFragmentToBottomFragment()
            )
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.sendSearchData(p0.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_NAMES = arrayOf("New", "Popular")
    }
}