package com.example.listingmovie.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listingmovie.R
import com.example.listingmovie.adapters.HomeAdapter
import com.example.listingmovie.databinding.FragmentHomeBinding
import com.example.listingmovie.helper.Status
import com.example.listingmovie.model.Result
import com.example.listingmovie.viewmodel.HomeViewModel
import com.example.listingmovie.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
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

    private fun setObserver(){

        viewModel.apply {
            movieNowPlaying().observe(viewLifecycleOwner) { resources ->
                when(resources.status){
                    Status.SUCCESS -> {
                        val data = resources.data
                        progressBar(false)
                        if(data != null){
                            setAdapter(data.results)
                        }else{
                            error("No result")
                        }

                    }

                    Status.ERROR -> {
                        progressBar(false)
                        error(resources.message ?: "Error")
                    }

                    Status.LOADING -> {
                        progressBar(true)
                    }
                }
            }

            getUser().observe(viewLifecycleOwner) { result ->
                binding.tvUsername.text = result.username
            }
        }
    }

    private fun setAdapter(data: List<Result?>?){
        val adapter = HomeAdapter()

        adapter.submitData(data)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = adapter
    }

    private fun progressBar(state: Boolean){
        binding.progressBar.isVisible = state
    }

    private fun error(message: String){
        binding.tvError.isVisible = true
        binding.tvError.text = message
    }

}