package com.example.gallery2.features.tabfragment.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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
    private var gLayoutManager: GridLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
        rvAddOnScrollListener()
    }

    private fun initViews() {
        gLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = gLayoutManager
        swipeRefreshLayout.setOnRefreshListener() {
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
            var previousTotal = 0
            val visibleThreshold = 5
            var loading = true
            var firstVisibleItem = 0
            var visibleItemCount = 0
            var totalItemCount = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = gLayoutManager?.childCount!!
                    totalItemCount = gLayoutManager?.itemCount!!
                    firstVisibleItem = gLayoutManager?.findFirstVisibleItemPosition()!!
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)
                    ) {
                        viewModel.loadNextPage()
                        loading = true;
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (gLayoutManager != null) {
            gLayoutManager = null
        }
    }
}