package com.example.gallery2.features.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery2.App
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.databinding.FragmentProfileBinding
import com.example.gallery2.features.feed.SharedViewModel
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>(), ProfileAdapter.OnPhotoClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val adapter: ProfileAdapter by lazy { ProfileAdapter(this) }
    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun getViewBinding(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
        rvAddOnScrollListener()
    }

    private fun initViews() {
        binding.rvProfile.adapter = adapter
        binding.rvProfile.layoutManager = GridLayoutManager(binding.root.context, 4)
        binding.ivTbSettings.setOnClickListener {
            sharedViewModel.openSettings()
        }
        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnRefresh.setOnClickListener {
            viewModel.loadProfile()
        }
    }

    override fun onPhotoClick(id: String) {
        sharedViewModel.sendPhotoId(id)
    }

    private fun setObservers() {
        viewModel.profileLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is ProfileState.Success
            val isError = state is ProfileState.Error
            val isLoading = state is ProfileState.Loading

            binding.pbLoading.isVisible = isLoading
            binding.groupMain.isVisible = isSuccess
            binding.groupError.isVisible = isError

            if (isSuccess) {
                binding.tvName.text = ((state as ProfileState.Success).name)
                binding.tvPhone.text = (state.phone)
                adapter.submitList(state.profile)
            }
        }
    }

    private fun rvAddOnScrollListener() {
        binding.rvProfile.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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