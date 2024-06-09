package com.example.listingmovie.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.listingmovie.R
import com.example.listingmovie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = com.example.listingmovie.presentation.fragments.DetailFragmentArgs.fromBundle(arguments as Bundle).result

        binding.tvTitle.text = result?.title
        binding.tvOverview.text = result?.overview

        val imgUrl = "https://image.tmdb.org/t/p/original${result?.backdropPath}"

        Glide.with(binding.imgPoster)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_broken_image_24)
            )
            .into(binding.imgPoster)

    }
}