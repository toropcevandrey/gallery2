package com.example.gallery2.features.settings

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.base.ValidateState
import com.example.gallery2.databinding.FragmentSettingsBinding
import com.example.gallery2.features.mainactivity.MainActivity
import com.example.gallery2.features.profile.ProfileFragmentDirections
import com.example.gallery2.utils.Constants.TOAST_USER_DIFFERENT_PASSWORDS
import com.example.gallery2.utils.Constants.TOAST_USER_INFO_UPDATED
import com.example.gallery2.utils.Constants.TOAST_USER_NOT_VALID_EMAIL
import com.example.gallery2.utils.Constants.TOAST_USER_NULL_FIELDS_SETTINGS
import com.example.gallery2.utils.Constants.TOAST_USER_PASSWORD_UPDATED
import com.example.gallery2.utils.Constants.TOAST_USER_WRONG_OLD_PASSWORD
import toast
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, factory).get(SettingsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        setObservers()
        viewModel.getUserInfo()
    }

    private fun initViews() {
        with(binding) {
            ivSettingsTbBack.setOnClickListener {
                findNavController().navigateUp()
            }

            tvSettingsSave.setOnClickListener {
                viewModel.validateData(
                    name = etSettingsName.text.toString(),
                    email = etSettingsEmail.text.toString(),
                    phone = etSettingsPhone.text.toString(),
                    oldPassword = etSettingsOldPassword.text.toString(),
                    newPassword = etSettingsNewSettingsPassword.text.toString(),
                    confirmPassword = etSettingsConfirmPassword.text.toString()
                )
            }

            btnSettingsRefresh.setOnClickListener {
                viewModel.getUserInfo()
            }

            tvSettingsDeleteYourAccount.setOnClickListener {
                viewModel.deleteUser()
            }

            tvSettingsSignOut.setOnClickListener {
                viewModel.unAuthorization()
                (requireActivity() as MainActivity).signOut()
            }
        }
    }

    private fun setObservers() {
        viewModel.settingsLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is SettingsState.Success
            val isError = state is SettingsState.Error
            val isLoading = state is SettingsState.Loading

            binding.pbSettingsLoading.isVisible = isLoading
            binding.groupSettingsMain.isVisible = isSuccess
            binding.groupSettingsError.isVisible = isError

            if (isSuccess) {
                val data = ((state as SettingsState.Success).settingsViewData)
                binding.etSettingsName.setText(data.username)
                binding.etSettingsPhone.setText(data.phone)
                binding.etSettingsEmail.setText(data.email)
            }
        }

        viewModel.settingsValidateLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is ValidateState.Success
            val isComparePassword = state is ValidateState.ComparePassword
            val isWrongEmail = state is ValidateState.WrongEmail
            val isWrongOldPassword = state is ValidateState.WrongOldPassword
            val isPasswordUpdated = state is ValidateState.PasswordUpdated
            val isEmpty = state is ValidateState.IsEmpty

            when {
                isSuccess -> {
                    requireContext().toast(TOAST_USER_INFO_UPDATED)
                    findNavController().navigateUp()
                }
                isComparePassword -> requireContext().toast(TOAST_USER_DIFFERENT_PASSWORDS)
                isWrongEmail -> requireContext().toast(TOAST_USER_NOT_VALID_EMAIL)
                isWrongOldPassword -> requireContext().toast(TOAST_USER_WRONG_OLD_PASSWORD)
                isPasswordUpdated -> requireContext().toast(TOAST_USER_PASSWORD_UPDATED)
                isEmpty -> requireContext().toast(TOAST_USER_NULL_FIELDS_SETTINGS)
            }
        }
    }
}