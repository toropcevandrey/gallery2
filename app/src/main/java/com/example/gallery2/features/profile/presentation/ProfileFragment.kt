package com.example.gallery2.features.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentProfileBinding
import com.example.gallery2.features.feed.presentation.SharedViewModel
import javax.inject.Inject

class ProfileFragment : Fragment(), ProfileAdapter.OnPhotoClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: ProfileViewModel? = null
    private var _binding: FragmentProfileBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupViewModel()
        initViews()
        setObservers()
        rvAddOnScrollListener()

        return binding.root
    }

    private fun initViews() {
        adapter = ProfileAdapter(this)
        binding.rvProfile.adapter = adapter
        binding.rvProfile.layoutManager = GridLayoutManager(binding.root.context, 4)
        binding.ivTbSettings.setOnClickListener {
            sharedViewModel.openSettings()
        }
        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnRefresh.setOnClickListener {
            viewModel?.loadProfile()
        }
    }


    override fun onPhotoClick(id: String) {
        sharedViewModel.sendPhotoId(id)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    private fun setObservers() {
        viewModel?.profileLiveData?.observe(viewLifecycleOwner) { state ->
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