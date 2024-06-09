package com.example.listingmovie.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listingmovie.R
import com.example.listingmovie.presentation.adapters.HomeAdapter
import com.example.listingmovie.databinding.FragmentHomeBinding
import com.example.listingmovie.helper.Status
import com.example.listingmovie.data.remote.response.Result
import com.example.listingmovie.presentation.state.MovieListState
import com.example.listingmovie.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

//    private val viewModel: HomeViewModel by viewModels {
//        ViewModelFactory.getInstance(requireContext())
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setObserver() {
        val movieListObserver = Observer<MovieListState> { state ->
            when (state) {
                is MovieListState.Success -> {
                    progressBar(false)
                    val data = state.movieResult
                    if (data.isNotEmpty()) {
                        setAdapter(data)
                    } else {
                        error("No result")
                    }
                }

                is MovieListState.Error -> {
                    progressBar(false)
                    error(state.error)
                }

                MovieListState.Loading -> {
                    progressBar(true)
                }
            }
        }

        homeViewModel.movieListState.observe(viewLifecycleOwner, movieListObserver)

        homeViewModel.getUser().observe(viewLifecycleOwner) { result ->
            binding.tvUsername.text = result.username
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter(data: List<Result?>?) {
        val adapter = HomeAdapter()

        adapter.submitData(data)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = adapter
    }

    private fun progressBar(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    private fun error(message: String) {
        binding.tvError.isVisible = true
        binding.tvError.text = message
    }

}