package com.example.gallery2.features.welcome.presentation

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
import com.example.gallery2.databinding.FragmentWelcomeBinding
import javax.inject.Inject

class WelcomeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WelcomeViewModel
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        bindViewModel()
        checkUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        initViews()
        setObservers()
        return binding.root
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this, factory).get(WelcomeViewModel::class.java)
    }

    private fun checkUser() {
        viewModel.postDataToRepository()
    }

    private fun initViews() {
        binding.btnRegistration.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigate_welcome_to_registration,
                null
            )
        )

        binding.btnAuthorization.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigate_welcome_to_authorization,
                null
            )
        )
    }

    private fun setObservers() {
        viewModel.welcomeLiveData.observe(viewLifecycleOwner) { state ->
            val isSuccess = state is WelcomeState.Success
            val isLoading = state is WelcomeState.Loading
            val isError = state is WelcomeState.Error

            binding.groupWelcome.isVisible = isError
            binding.pbWelcomeLoading.isVisible = isLoading

            if (isSuccess) {
                findNavController().navigate(
                    WelcomeFragmentDirections.navigateWelcomeFragmentToHomeFragment()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}