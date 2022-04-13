package com.example.gallery2.features.addphoto.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentAddPhotoBinding
import com.example.gallery2.utils.Constants.PHOTO_URI
import com.example.gallery2.utils.Constants.UPLOAD_PHOTO_SUCCESS
import toast
import javax.inject.Inject

class AddPhotoFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: AddPhotoViewModel
    private var _binding: FragmentAddPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        App.component.inject(this)
        bindVm()
        initViews()
        setObservers()
        return binding.root
    }

    private fun initViews() {
        Glide.with(this)
            .load(arguments?.getString(PHOTO_URI))
            .into(binding.ivPhoto)

        binding.btnRefresh.setOnClickListener {
            postDataToVm()
        }

        binding.tbAddPhoto.setOnClickListener {
            postDataToVm()
        }
    }

    private fun bindVm() {
        viewModel = ViewModelProvider(this, factory).get(AddPhotoViewModel::class.java)
    }

    private fun postDataToVm() {
        viewModel.sendMediaFile(
            name = binding.etPhotoName.text.toString(),
            newFile = arguments?.getString(PHOTO_URI) ?: "",
            description = binding.etDescription.text.toString()
        )
    }

    private fun setObservers() {
        viewModel.addPhotoLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is AddPhotoState.Success
            val isLoading = state is AddPhotoState.Loading
            val isError = state is AddPhotoState.Error
            val isInit = state is AddPhotoState.FirstInit

            binding.groupError.isVisible = isError
            binding.groupAddPhoto.isVisible = isInit
            binding.pbLoading.isVisible = isLoading

            if (isSuccess) {
                requireContext().toast(UPLOAD_PHOTO_SUCCESS)
                findNavController().navigate(AddPhotoFragmentDirections.navigateAddPhotoFragmentToFeedFragment())
            }
        }
    }
}