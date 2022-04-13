package com.example.gallery2.features.registration.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.api.models.ValidateState
import com.example.gallery2.databinding.FragmentRegistrationBinding
import com.example.gallery2.utils.Constants.ERROR_CONNECTION
import com.example.gallery2.utils.Constants.TOAST_USER_DIFFERENT_PASSWORDS
import com.example.gallery2.utils.Constants.TOAST_USER_NOT_VALID_EMAIL
import com.example.gallery2.utils.Constants.TOAST_USER_NULL_FIELDS
import com.example.gallery2.utils.Constants.TOAST_USER_REGISTRATION_SUCCESS
import toast
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: RegistrationViewModel
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        App.component.inject(this)
        viewModel = ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
        initViews()
        setObservers()

        return binding.root
    }

    private fun initViews() {
        binding.btnAuthorization.setOnClickListener {
            findNavController().navigate(
                RegistrationFragmentDirections.navigateRegistrationToAuthorization()
            )
        }

        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegistration.setOnClickListener {
            viewModel.validateData(
                name = binding.etUserName.text.toString(),
                phone = binding.etPhone.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
                confirmPassword = binding.etConfirmPassword.text.toString()
            )
        }
    }

    private fun setObservers() {
        viewModel.registrationLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is RegistrationState.Error
            val isSuccess = state is RegistrationState.Success
            val isLoading = state is RegistrationState.Loading
            val isFirstInit = state is RegistrationState.FirstInit

            binding.groupMain.isVisible = isFirstInit
            binding.pbLoading.isVisible = isLoading

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}