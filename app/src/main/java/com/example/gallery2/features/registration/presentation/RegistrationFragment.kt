package com.example.gallery2.features.registration.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.gallery2.App
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentRegistrationBinding
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        val view = binding.root
        App.App.getComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
        initViews()
        setObservers()

        return view
    }

    private fun initViews() {
        binding.btnAuthorization.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigate_registration_to_authorization,
                null
            )
        )

        binding.btnRegistration.setOnClickListener {
            viewModel.postDataToRepository(
                binding.etRegistrationUserName.text.toString(),
                binding.etRegistrationPhone.text.toString(),
                binding.etRegistrationEmail.text.toString(),
                binding.etRegistrationPassword.text.toString(),
                //add confirm password
            )
        }
    }

    private fun setObservers() {
        viewModel.registrationLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is RegistrationState.Error
            val isSuccess = state is RegistrationState.Success
            val isLoading = state is RegistrationState.Loading
            val isFirstInit = state is RegistrationState.FirstInit

            binding.groupRegistration.isVisible = isFirstInit
            binding.pbRegistrationLoading.isVisible = isLoading


            if (isSuccess) {
                view?.findNavController()?.navigate(
                    R.id.navigate_registration_to_mainFragment,
                    null
                )
            }

            if (isError) {
                Toast.makeText(activity, "123", Toast.LENGTH_SHORT).show()
            }

        }
    }

}