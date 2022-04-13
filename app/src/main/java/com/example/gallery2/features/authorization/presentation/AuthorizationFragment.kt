package com.example.gallery2.features.authorization.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentAuthorizationBinding
import com.example.gallery2.utils.Constants.TOAST_USER_NOT_VALID_PAIR_EMAIL_PASSWORD
import toast
import javax.inject.Inject

class AuthorizationFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthorizationViewModel
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val view = binding.root
        App.component.inject(this)
        viewModel = ViewModelProvider(this, factory).get(AuthorizationViewModel::class.java)
        initViews()
        setObservers()

        return view
    }

    private fun initViews() {
        binding.btnRegistration.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigate_authorization_to_registration,
                null
            )
        )
        binding.ivTbBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnAuthorization.setOnClickListener {
            viewModel.postDataToRepository(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    private fun setObservers() {
        viewModel.authorizationLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is AuthorizationState.Error
            val isSuccess = state is AuthorizationState.Success
            val isLoading = state is AuthorizationState.Loading
            val isFirstInit = state is AuthorizationState.FirstInit

            binding.groupMain.isVisible = isFirstInit
            binding.pbLoading.isVisible = isLoading

            if (isSuccess) {
                findNavController().navigate(
                    AuthorizationFragmentDirections.navigateAuthorizationToHomeFragment()
                )
            }

            if (isError) {
                requireActivity().toast(TOAST_USER_NOT_VALID_PAIR_EMAIL_PASSWORD)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}