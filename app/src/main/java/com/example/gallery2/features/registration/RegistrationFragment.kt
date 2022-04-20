package com.example.gallery2.features.registration

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.base.ValidateState
import com.example.gallery2.databinding.FragmentRegistrationBinding
import com.example.gallery2.utils.Constants.ERROR_CONNECTION
import com.example.gallery2.utils.Constants.TOAST_USER_DIFFERENT_PASSWORDS
import com.example.gallery2.utils.Constants.TOAST_USER_NOT_VALID_EMAIL
import com.example.gallery2.utils.Constants.TOAST_USER_NULL_FIELDS
import com.example.gallery2.utils.Constants.TOAST_USER_REGISTRATION_SUCCESS
import toast
import javax.inject.Inject

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun getViewBinding(): FragmentRegistrationBinding =
        FragmentRegistrationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun initViews() {
        binding.btnRegistrationAuthorization.setOnClickListener {
            findNavController().navigate(
                RegistrationFragmentDirections.navigateRegistrationToAuthorization()
            )
        }

        binding.ivRegistrationTbBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegistrationRegistration.setOnClickListener {
            viewModel.validateData(
                name = binding.etRegistrationUserName.text.toString(),
                phone = binding.etRegistrationPhone.text.toString(),
                email = binding.etRegistrationEmail.text.toString(),
                password = binding.etRegistrationPassword.text.toString(),
                confirmPassword = binding.etRegistrationConfirmPassword.text.toString()
            )
        }
    }

    private fun setObservers() {
        viewModel.registrationLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is RegistrationState.Error
            val isSuccess = state is RegistrationState.Success
            val isLoading = state is RegistrationState.Loading

            binding.groupRegistrationMain.isVisible = !isLoading && !isSuccess
            binding.pbRegistrationLoading.isVisible = isLoading

            if (isSuccess) {
                findNavController().navigate(
                    RegistrationFragmentDirections.navigateRegistrationToHomeFragment()
                )
            }

            if (isError) {
                requireContext().toast(ERROR_CONNECTION)
            }

        }

        viewModel.regValidateLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is ValidateState.Success
            val isComparePassword = state is ValidateState.ComparePassword
            val isWrongEmail = state is ValidateState.WrongEmail
            val isEmpty = state is ValidateState.IsEmpty

            when {
                isSuccess -> requireContext().toast(TOAST_USER_REGISTRATION_SUCCESS)
                isComparePassword -> requireContext().toast(TOAST_USER_DIFFERENT_PASSWORDS)
                isWrongEmail -> requireContext().toast(TOAST_USER_NOT_VALID_EMAIL)
                isEmpty -> requireContext().toast(TOAST_USER_NULL_FIELDS)
            }
        }
    }
}