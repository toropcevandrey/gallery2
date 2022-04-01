package com.example.gallery2.features.authorization.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentAuthorizationBinding
import com.example.gallery2.features.registration.presentation.RegistrationFragmentDirections
import com.example.gallery2.features.registration.presentation.RegistrationState
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

        binding.btnAuthorization.setOnClickListener {
            viewModel.postDataToRepository(
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
            val isFirstInit = state is AuthorizationState.FirstInit

            binding.groupAuthorization.isVisible = isFirstInit
            binding.pbAuthorizationLoading.isVisible = isLoading


            if (isSuccess) {
                findNavController().navigate(
                    AuthorizationFragmentDirections.navigateAuthorizationToHomeFragment()
                )
            }

            if (isError) {
                Toast.makeText(activity, "123", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}