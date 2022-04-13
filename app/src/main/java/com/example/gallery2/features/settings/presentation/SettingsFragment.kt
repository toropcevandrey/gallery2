package com.example.gallery2.features.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.api.models.ValidateState
import com.example.gallery2.databinding.FragmentSettingsBinding
import com.example.gallery2.features.mainactivity.MainActivity
import com.example.gallery2.utils.Constants.TOAST_USER_DIFFERENT_PASSWORDS
import com.example.gallery2.utils.Constants.TOAST_USER_INFO_UPDATED
import com.example.gallery2.utils.Constants.TOAST_USER_NOT_VALID_EMAIL
import com.example.gallery2.utils.Constants.TOAST_USER_NULL_FIELDS_SETTINGS
import com.example.gallery2.utils.Constants.TOAST_USER_PASSWORD_UPDATED
import com.example.gallery2.utils.Constants.TOAST_USER_WRONG_OLD_PASSWORD
import toast
import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: SettingsViewModel? = null
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        App.component.inject(this)
        viewModel = ViewModelProvider(this, factory).get(SettingsViewModel::class.java)
        initViews()
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel?.getUserInfo()
    }

    private fun initViews() {
        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvSave.setOnClickListener {
            viewModel?.validateData(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                phone = binding.etPhone.text.toString(),
                oldPassword = binding.etOldPassword.text.toString(),
                newPassword = binding.etNewPassword.text.toString(),
                confirmPassword = binding.etConfirmPassword.text.toString()
            )
        }

        binding.btnRefresh.setOnClickListener {
            viewModel?.getUserInfo()
        }

        binding.tvDeleteYourAccount.setOnClickListener {
            viewModel?.deleteUser()
        }

        binding.tvSignOut.setOnClickListener {
            viewModel?.unAuthorization()
            (requireActivity() as MainActivity).signOut()
        }
    }

    private fun setObservers() {
        viewModel?.settingsLiveData?.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is SettingsState.Success
            val isError = state is SettingsState.Error
            val isLoading = state is SettingsState.Loading

            binding.pbLoading.isVisible = isLoading
            binding.groupMain.isVisible = isSuccess
            binding.groupError.isVisible = isError

            if (isSuccess) {
                binding.etName.setText((state as SettingsState.Success).settingsViewData.username)
                binding.etPhone.setText(state.settingsViewData.phone)
                binding.etEmail.setText(state.settingsViewData.email)
            }
        }

        viewModel?.settingsValidateLiveData?.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is ValidateState.Success
            val isComparePassword = state is ValidateState.ComparePassword
            val isWrongEmail = state is ValidateState.WrongEmail
            val isWrongOldPassword = state is ValidateState.WrongOldPassword
            val isPasswordUpdated = state is ValidateState.PasswordUpdated
            val isEmpty = state is ValidateState.IsEmpty

            when {
                isSuccess -> requireContext().toast(TOAST_USER_INFO_UPDATED)
                isComparePassword -> requireContext().toast(TOAST_USER_DIFFERENT_PASSWORDS)
                isWrongEmail -> requireContext().toast(TOAST_USER_NOT_VALID_EMAIL)
                isWrongOldPassword -> requireContext().toast(TOAST_USER_WRONG_OLD_PASSWORD)
                isPasswordUpdated -> requireContext().toast(TOAST_USER_PASSWORD_UPDATED)
                isEmpty -> requireContext().toast(TOAST_USER_NULL_FIELDS_SETTINGS)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}