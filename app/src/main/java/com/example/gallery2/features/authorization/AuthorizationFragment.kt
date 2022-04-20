package com.example.gallery2.features.authorization

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.base.ValidateState
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.databinding.FragmentAuthorizationBinding
import com.example.gallery2.utils.Constants.TOAST_USER_NOT_VALID_PAIR_EMAIL_PASSWORD
import com.example.gallery2.utils.Constants.TOAST_USER_NULL_FIELDS_SETTINGS
import toast
import javax.inject.Inject

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: AuthorizationViewModel by lazy {
        ViewModelProvider(this, factory).get(AuthorizationViewModel::class.java)
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

    override fun getViewBinding(): FragmentAuthorizationBinding =
        FragmentAuthorizationBinding.inflate(layoutInflater)

    private fun initViews() {
        binding.btnAuthorizationRegistration.setOnClickListener {
            findNavController().navigate(
                AuthorizationFragmentDirections.navigateAuthorizationToRegistration()
            )
        }
        binding.ivAuthorizationTbBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAuthorization.setOnClickListener {
            viewModel.checkNotNullFields(
                binding.etAuthorizationEmail.text.toString(),
                binding.etAuthorizationPassword.text.toString()
            )
        }
    }

    private fun setObservers() {
        viewModel.authorizationLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is AuthorizationState.Error
            val isSuccess = state is AuthorizationState.Success
            val isLoading = state is AuthorizationState.Loading

            binding.groupAuthorizationMain.isVisible = !isLoading && !isSuccess
            binding.pbAuthorizationLoading.isVisible = isLoading

            if (isSuccess) {
                findNavController().navigate(
                    AuthorizationFragmentDirections.navigateAuthorizationToHomeFragment()
                )
            }

            if (isError) {
                requireContext().toast(TOAST_USER_NOT_VALID_PAIR_EMAIL_PASSWORD)
            }
        }

        viewModel.validateLiveData.observe(viewLifecycleOwner) { state ->
            val isEmpty = state is ValidateState.IsEmpty

            if (isEmpty) {
                requireContext().toast(TOAST_USER_NULL_FIELDS_SETTINGS)
            }
        }
    }
}