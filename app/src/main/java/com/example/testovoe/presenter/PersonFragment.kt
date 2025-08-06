package com.example.testovoe.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testovoe.R
import com.example.testovoe.databinding.FragmentPersonBinding
import com.example.testovoe.domain.models.FailureType
import com.example.testovoe.domain.models.PersonPreview
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.util.Debouncer

class PersonFragment : Fragment() {
    private lateinit var binding: FragmentPersonBinding
    private val personViewModel: PersonViewModel by viewModel()
    private val adapter by lazy {
        PersonAdapter(requireContext(), mutableListOf(), ::onPersonClick)
    }
    private lateinit var debouncer: Debouncer
    private val recyclerView: RecyclerView get() = binding.recyclerViewId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debouncer = Debouncer(scope = viewLifecycleOwner.lifecycleScope)
        setupRv()
        observePerson()
        setupRefreshListener()
        newRequestButtonListener()
    }

    private fun setupRv() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onPersonClick(person: PersonPreview) {
        if (debouncer.clickDebounce()) {
            val action = PersonFragmentDirections
                .actionPersonFragmentToPersonDetailsFragment(person)
            findNavController().navigate(action)
        }
    }


    private fun observePerson() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                personViewModel.state.collect { state ->
                    when (state) {
                        is PersonState.Loading -> {
                            showLoading()
                        }

                        is PersonState.Content -> {
                            showContent(state.persons)
                        }

                        is PersonState.Error -> {
                            when (state.errorType) {
                                FailureType.NoInternet -> {
                                    showInternetErrorPreview()
                                }

                                FailureType.ApiError -> {
                                    showApiErrorPreview()
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.noInternetPreviewId.visibility = View.GONE
        binding.recyclerViewId.visibility = View.GONE
        binding.progressBarId.visibility = View.VISIBLE
    }

    private fun showContent(persons: List<PersonPreview>) {
        binding.progressBarId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.recyclerViewId.visibility = View.VISIBLE
        binding.swipeRefreshLayoutId.isRefreshing = false
        adapter.setList(persons)
    }

    private fun showApiErrorPreview() {
        binding.progressBarId.visibility = View.GONE
        binding.textViewId.text = getString(R.string.api_error_tv)
        binding.recyclerViewId.visibility = View.GONE
        binding.swipeRefreshLayoutId.isRefreshing = false
        binding.noInternetPreviewId.visibility = View.VISIBLE
    }

    private fun showInternetErrorPreview() {
        binding.textViewId.text = getString(R.string.no_internet_tv)
        binding.recyclerViewId.visibility = View.GONE
        binding.swipeRefreshLayoutId.isRefreshing = false
        binding.noInternetPreviewId.visibility = View.VISIBLE
        binding.progressBarId.visibility = View.GONE
    }

    private fun newRequestButtonListener() {
        binding.retryButtonId.setOnClickListener {
            personViewModel.getPersonFromInternet()
        }
    }

    private fun setupRefreshListener() {
        binding.swipeRefreshLayoutId.setOnRefreshListener {
            personViewModel.getPersonFromInternet()
        }
    }

}