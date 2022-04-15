package com.example.gallery2.features.tabfragment.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.features.feed.SharedViewModel
import com.example.gallery2.features.tabfragment.TabState
import com.example.gallery2.features.tabfragment.adapter.TabListAdapter

abstract class BaseTabFragment<VB : ViewBinding> : BaseFragment<VB>(), TabListAdapter.OnPhotoClickListener {

    abstract override val viewModel: BaseTabViewModel
    protected abstract var recyclerView: RecyclerView
    protected abstract var swipeRefreshLayout: SwipeRefreshLayout
    protected abstract var errorImageView: ImageView
    protected abstract var progressBarBottom: ProgressBar
    protected abstract var progressBarCenter: ProgressBar

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val listAdapter: TabListAdapter by lazy { TabListAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
        rvAddOnScrollListener()
    }

    private fun initViews() {
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    override fun onPhotoClick(id: String) {
        sharedViewModel.sendPhotoId(id)
    }

    private fun setObservers() {
        viewModel.tabLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is TabState.Error
            val isLoadingBottom = state is TabState.LoadingBottom
            val isSuccess = state is TabState.Success
            val isLoadingCenter = state is TabState.LoadingCenter

            recyclerView.isVisible = isSuccess || isLoadingBottom
            errorImageView.isVisible = isError
            progressBarBottom.isVisible = isLoadingBottom
            progressBarCenter.isVisible = isLoadingCenter
            swipeRefreshLayout.isRefreshing = false

            if (isSuccess) {
                listAdapter.submitList((state as TabState.Success).tab)
            }
        }

        sharedViewModel.searchEvent.observe(viewLifecycleOwner) { query ->
            viewModel.onSearchEntered(query)
        }
    }

    private fun rvAddOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isLoaded = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoaded
                ) {
                    isLoaded = true
                    viewModel.loadNextPage()
                }
                isLoaded = false
            }
        })
    }
}