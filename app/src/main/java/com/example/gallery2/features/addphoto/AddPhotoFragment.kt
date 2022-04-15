package com.example.gallery2.features.addphoto

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gallery2.App
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.databinding.FragmentAddPhotoBinding
import com.example.gallery2.utils.Constants.PHOTO_URI
import com.example.gallery2.utils.Constants.UPLOAD_PHOTO_SUCCESS
import toast
import javax.inject.Inject

class AddPhotoFragment : BaseFragment<FragmentAddPhotoBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: AddPhotoViewModel by lazy {
        ViewModelProvider(this, factory).get(AddPhotoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    override fun getViewBinding(): FragmentAddPhotoBinding =
        FragmentAddPhotoBinding.inflate(layoutInflater)

    private fun initViews() {
        binding.ivPhoto.let {
            Glide.with(this)
                .load(arguments?.getString(PHOTO_URI))
                .into(it)
        }

        binding.btnRefresh.setOnClickListener {
            postDataToVm()
        }

        binding.tbAddPhoto.setOnClickListener {
            postDataToVm()
        }
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

            binding.groupError.isVisible = isError
            binding.groupAddPhoto.isVisible = !isError && !isLoading && !isSuccess
            binding.pbLoading.isVisible = isLoading

            if (isSuccess) {
                requireContext().toast(UPLOAD_PHOTO_SUCCESS)
                findNavController().navigate(AddPhotoFragmentDirections.navigateAddPhotoFragmentToFeedFragment())
            }
        }
    }
}