package com.example.listingmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.listingmovie.data.remote.response.Result
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.listingmovie.R
import com.example.listingmovie.databinding.ItemMovieBinding
import com.example.listingmovie.fragments.HomeFragmentDirections

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Result?>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result){
            binding.apply {
                tvJudul.text = result.title
                tvTanggal.text = result.releaseDate

                val imgUrl = "https://image.tmdb.org/t/p/original${result.posterPath}"

                Glide.with(imgPoster)
                    .load(imgUrl)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.baseline_image_24)
                        .error(R.drawable.baseline_broken_image_24)
                    )
                    .into(imgPoster)

                root.setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(result)
                    action.result = result
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}