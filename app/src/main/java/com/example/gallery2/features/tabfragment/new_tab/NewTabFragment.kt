package com.example.gallery2.features.tabfragment.new_tab

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentNewTabBinding
import com.example.gallery2.features.tabfragment.base.BaseTabFragment
import javax.inject.Inject

class NewTabFragment : BaseTabFragment<FragmentNewTabBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override val viewModel: NewTabViewModel by lazy {
        ViewModelProvider(this, factory).get(NewTabViewModel::class.java)
    }

    override lateinit var recyclerView: RecyclerView
    override lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override lateinit var errorImageView: ImageView
    override lateinit var progressBarBottom: ProgressBar
    override lateinit var progressBarCenter: ProgressBar

    override fun getViewBinding(): FragmentNewTabBinding = FragmentNewTabBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvTabNew
        swipeRefreshLayout = binding.swipeRefreshNew
        errorImageView = binding.ivErrorNew
        progressBarBottom = binding.pbNewBottom
        progressBarCenter = binding.pbNewCentre

        super.onViewCreated(view, savedInstanceState)
    }
}