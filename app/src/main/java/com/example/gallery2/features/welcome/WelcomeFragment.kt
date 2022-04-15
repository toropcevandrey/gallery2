package com.example.gallery2.features.welcome

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallery2.App
import com.example.gallery2.base.BaseFragment
import com.example.gallery2.databinding.FragmentWelcomeBinding
import javax.inject.Inject

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: WelcomeViewModel by lazy {
        ViewModelProvider(this, factory).get(WelcomeViewModel::class.java)
    }

    override fun getViewBinding() = FragmentWelcomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        checkUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setObservers()
    }

    private fun checkUser() {
        viewModel.checkAvailabilityTokens()
    }

    private fun bindViews() {
        binding.btnRegistration.setOnClickListener {
            findNavController().navigate(
                WelcomeFragmentDirections.navigateWelcomeToRegistration()
            )
        }

        binding.btnAuthorization.setOnClickListener {
            findNavController().navigate(
                WelcomeFragmentDirections.navigateWelcomeToAuthorization()
            )
        }
    }

    private fun setObservers() {
        viewModel.welcomeEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                is WelcomeEvents.OpenMainScreen -> {
                    binding.groupMain.isVisible = false
                    findNavController().navigate(
                        WelcomeFragmentDirections.navigateWelcomeFragmentToHomeFragment()
                    )
                }
                is WelcomeEvents.OpenAuthScreen -> binding.groupMain.isVisible = true
            }
        }
    }
}