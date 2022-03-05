package com.example.gallery2.features.authorization.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentAuthorizationBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Authorization.newInstance] factory method to
 * create an instance of this fragment.
 */
class Authorization : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val view = binding.root
        initViews()

        return view
    }

    private fun initViews() {
        binding.btnRegistration.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.navigate_authorization_to_registration,
                null
            )
        )
    }
}