package com.example.gallery2.features.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentWelcomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root

        initViews()

        return view
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}