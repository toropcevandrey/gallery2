package com.example.gallery2.features.tabfragment.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentTabBinding
import com.example.gallery2.features.feed.presentation.SharedViewModel
import com.example.gallery2.utils.Constants.ARG_TABS
import javax.inject.Inject

class TabFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: TabViewModel? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var rvTab: RecyclerView
    private var listAdapter = TabListAdapter()
    private lateinit var pg: ProgressBar
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        App.component.inject(this)
        viewModel = ViewModelProvider(this, factory).get(TabViewModel::class.java)
        initViews()
        setObservers()
        rvAddOnScrollListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_TABS) }?.apply {
            viewModel?.postDataToRepository(getInt(ARG_TABS))
        }
    }

    private fun initViews() {
        pg = binding.pg
        rvTab = binding.rvTab
        rvTab.adapter = listAdapter
        rvTab.layoutManager = GridLayoutManager(binding.root.context, 2)

    }

    private fun setObservers() {
        viewModel?.tabLiveData?.observe(viewLifecycleOwner) { state ->
            val isError = state is TabState.Error
            val isLoading = state is TabState.Loading
            val isSuccess = state is TabState.Success

            pg.isVisible = isLoading
            if (isSuccess) {
                listAdapter.submitList((state as TabState.Success).tab)
            }
        }

        sharedViewModel.searchData.observe(viewLifecycleOwner) { query ->
            viewModel?.onSearchEntered(query)
        }
    }

    private fun rvAddOnScrollListener() {
        rvTab.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isLoaded = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoaded
                ) {
                    isLoaded = true
                    viewModel?.loadNextPage()
                }
                isLoaded = false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}