package com.example.gallery2.features.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: SettingsViewModel? = null
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etOldPassword: EditText
    private lateinit var btnSave: TextView


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
        etName = binding.etName
        etPhone = binding.etPhone
        etEmail = binding.etEmail
        btnSave = binding.tvSave
        etOldPassword = binding.etOldPassword
        btnSave.setOnClickListener {
            viewModel?.saveUserInfo(
                username = etName.text.toString(),
                email = etEmail.text.toString(),
                phone = etPhone.text.toString(),
                password = etOldPassword.text.toString()
            )
        }

    }

    private fun setObservers() {
        viewModel?.settingsLiveData?.observe(viewLifecycleOwner, Observer { data ->
            etName.setText(data.username)
            etPhone.setText(data.phone)
            etEmail.setText(data.email)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}