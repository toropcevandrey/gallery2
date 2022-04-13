package com.example.gallery2.features.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gallery2.R
import com.example.gallery2.databinding.FragmentHomeBinding
import com.example.gallery2.features.feed.presentation.SharedViewModel
import com.example.gallery2.utils.Constants.BACK_PRESSED
import toast

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var backPressed: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        setObservers()
        closeApp()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.home_fragment_nav_host_fragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
    }

    private fun closeApp() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (backPressed + 2000 > System.currentTimeMillis()) requireActivity().finish()
            else requireContext().toast(BACK_PRESSED)
            backPressed = System.currentTimeMillis()
        }
    }


    private fun setObservers() {
        sharedViewModel.observeOpenPhoto().observe(viewLifecycleOwner) {
            findNavController().navigate(
                HomeFragmentDirections.navigateHomeFragmentToOpenPhotoFragment(it),
            )
        }

        sharedViewModel.observeOpenSettings().observe(viewLifecycleOwner){
            findNavController().navigate(
                HomeFragmentDirections.navigateHomeFragmentToSettingsFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}