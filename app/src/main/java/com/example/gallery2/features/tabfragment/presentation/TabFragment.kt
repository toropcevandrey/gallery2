package com.example.gallery2.features.tabfragment.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentTabBinding
import com.example.gallery2.features.feed.presentation.SharedViewModel
import com.example.gallery2.utils.Constants.ARG_TABS
import javax.inject.Inject

class TabFragment : Fragment(), TabListAdapter.OnPhotoClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: TabViewModel? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var listAdapter: TabListAdapter
    private var tab = 1
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
            tab = getInt(ARG_TABS)
            viewModel?.postDataToRepository(tab)
        }
    }

    private fun initViews() {
        listAdapter = TabListAdapter(this)
        binding.rvTab.adapter = listAdapter
        binding.rvTab.layoutManager = GridLayoutManager(binding.root.context, 2)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel?.refreshData(tab)
        }
    }

    override fun onPhotoClick(id: String) {
        sharedViewModel.sendPhotoId(id)
    }

    private fun setObservers() {
        viewModel?.tabLiveData?.observe(viewLifecycleOwner) { state ->
            val isError = state is TabState.Error
            val isLoading = state is TabState.Loading
            val isSuccess = state is TabState.Success

            binding.rvTab.isVisible = isSuccess || isLoading
            binding.rvTab.isGone = isError
            binding.ivError.isVisible = isError
            binding.pg.isVisible = isLoading
            binding.swipeRefresh.isRefreshing = false

            if (isSuccess) {
                listAdapter.submitList((state as TabState.Success).tab)
            }
        }

        sharedViewModel.searchData.observe(viewLifecycleOwner) { query ->
            viewModel?.onSearchEntered(query)
        }
    }

    private fun rvAddOnScrollListener() {
        binding.rvTab.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        binding.swipeRefresh.removeAllViews()
        _binding = null
    }
}