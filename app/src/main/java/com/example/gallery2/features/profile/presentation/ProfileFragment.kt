package com.example.gallery2.features.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.App
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentProfileBinding
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvProfile: RecyclerView
    private var adapter = ProfileAdapter()

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

        return binding.root
    }

    private fun initViews() {
        rvProfile = binding.rvProfile
        rvProfile.adapter = adapter
        rvProfile.layoutManager = GridLayoutManager(binding.root.context, 4)
        binding.ivTbSettings.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigate_profileFragment_to_settingsFragment,
                null
            )
        )
        binding.ivTbBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.feed_graph
            )
        )
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    private fun setObservers(){
        viewModel.profileLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is ProfileState.Success
            val isError = state is ProfileState.Error

            if (isSuccess){
                binding.tvProfileName.text = ((state as ProfileState.Success).name)
                binding.tvProfilePhone.text = (state.phone)
                adapter.submitList(state.profile)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}