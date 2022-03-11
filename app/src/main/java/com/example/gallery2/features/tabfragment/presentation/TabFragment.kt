package com.example.gallery2.features.tabfragment.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery2.App
import com.example.gallery2.databinding.FragmentTabBinding
import com.example.gallery2.utils.Constants.ARG_TABS
import javax.inject.Inject

class TabFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: TabViewModel? = null
    private lateinit var rvTab: RecyclerView
    private lateinit var listAdapter: TabListAdapter
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        App.getComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(TabViewModel::class.java)
        setObservers()
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_TABS) }?.apply {
            when (getInt(ARG_TABS)) {
                0 -> postDataToVm(news = true, popular = false)
                1 -> postDataToVm(news = false, popular = true)
            }
        }
    }

    private fun postDataToVm(news: Boolean, popular: Boolean) {
        viewModel?.postDataToRepository(news, popular)
    }

    private fun initViews() {
        rvTab = binding.rvTab
        listAdapter = TabListAdapter()
        rvTab.adapter = listAdapter
        rvTab.layoutManager = GridLayoutManager(binding.root.context, 2)

    }

    private fun setObservers() {
        viewModel?.tabViewData?.observe(viewLifecycleOwner) { list ->
            listAdapter.submitList(list)

        }
    }
}