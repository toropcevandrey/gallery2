package com.example.gallery2.features.openphoto

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallery2.App
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.databinding.FragmentOpenPhotoBinding
import com.example.gallery2.utils.Constants.BASE_URL_MEDIA
import javax.inject.Inject

class OpenPhotoFragment : BaseFragment<FragmentOpenPhotoBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: OpenPhotoViewModel by lazy {
        ViewModelProvider(this, factory).get(OpenPhotoViewModel::class.java)
    }

    private val circularProgressDrawable: CircularProgressDrawable by lazy {
        CircularProgressDrawable(requireContext())
            .apply {
                with(this) {
                    strokeWidth = 5f
                    centerRadius = 50f
                    start()
                }
            }
    }

    private val args: OpenPhotoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPhoto(args.openPhoto)
        setObservers()
        initViews()
    }

    override fun getViewBinding(): FragmentOpenPhotoBinding = FragmentOpenPhotoBinding.inflate(layoutInflater)

    private fun initViews() {
        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        viewModel.openPhotoLiveData.observe(viewLifecycleOwner) { state ->
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