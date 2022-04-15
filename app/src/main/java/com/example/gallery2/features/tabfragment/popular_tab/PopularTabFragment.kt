package com.example.gallery2.features.tabfragment.popular_tab

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentPopularTabBinding
import com.example.gallery2.features.tabfragment.base.BaseTabFragment
import javax.inject.Inject

class PopularTabFragment : BaseTabFragment<FragmentPopularTabBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override val viewModel: PopularTabViewModel by lazy {
        ViewModelProvider(this, factory).get(PopularTabViewModel::class.java)
    }

    override lateinit var recyclerView: RecyclerView
    override lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override lateinit var errorImageView: ImageView
    override lateinit var progressBarBottom: ProgressBar
    override lateinit var progressBarCenter: ProgressBar

    override fun getViewBinding(): FragmentPopularTabBinding = FragmentPopularTabBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvTabPopular
        swipeRefreshLayout = binding.swipeRefreshPopular
        errorImageView = binding.ivErrorPopular
        progressBarBottom = binding.pbPopularBottom
        progressBarCenter = binding.pbPopularCenter

        super.onViewCreated(view, savedInstanceState)
    }
}