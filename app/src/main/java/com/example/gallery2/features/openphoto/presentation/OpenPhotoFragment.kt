package com.example.gallery2.features.openphoto.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentOpenPhotoBinding
import com.example.gallery2.utils.Constants.BASE_URL_MEDIA
import com.example.gallery2.utils.Constants.BUNDLE_PHOTO_ID
import javax.inject.Inject

class OpenPhotoFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: OpenPhotoViewModel? = null
    private var _binding: FragmentOpenPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenPhotoBinding.inflate(inflater, container, false)
        App.component.inject(this)
        viewModel = ViewModelProvider(this, factory).get(OpenPhotoViewModel::class.java)

        init()
        setObservers()
        initViews()

        return binding.root
    }

    private fun init() {
        arguments?.getString(BUNDLE_PHOTO_ID)?.let { viewModel?.getPhoto(it) }
    }

    private fun setGlidePlaceholder() {
        circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()
    }

    private fun initViews() {
        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        setGlidePlaceholder()
        viewModel?.openPhotoLiveData?.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is OpenPhotoState.Success
            val isError = state is OpenPhotoState.Error
            val isLoading = state is OpenPhotoState.Loading

            binding.pbLoading.isVisible = isLoading
            binding.groupMain.isVisible = isSuccess
            binding.groupError.isVisible = isError

            if (isSuccess) {
                binding.tvPhotoName.text = (state as OpenPhotoState.Success).openPhotoViewData.name
                binding.tvUserName.text = state.userName
                binding.tvDescription.text = state.openPhotoViewData.description
                Glide.with(this)
                    .load(BASE_URL_MEDIA + state.openPhotoViewData.image)
                    .placeholder(circularProgressDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivPhoto)
            }
        }
    }
}