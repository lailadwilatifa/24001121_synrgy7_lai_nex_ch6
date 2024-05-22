package com.example.listingmovie.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.listingmovie.R
import com.example.listingmovie.databinding.FragmentProfileBinding
import com.example.listingmovie.model.User
import com.example.listingmovie.viewmodel.ProfileViewModel
import com.example.listingmovie.viewmodel.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()

        binding.btnUpdate.setOnClickListener {
            val user = User(
                binding.usernameEditText.text.toString(),
                "",
                "",
                binding.namaLengkapEditText.text.toString(),
                binding.tanggalLahirEditText.text.toString(),
                binding.alamatEditText.text.toString()
            )

            viewModel.saveProfile(user)
            Toast.makeText(requireContext(), "Berhasil mengupdate profile", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.saveLogin(false)
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build()
            )
        }
    }

    private fun setObserver() {
        viewModel.getUser().observe(viewLifecycleOwner) { result ->
            binding.usernameEditText.setText(result.username)
            binding.namaLengkapEditText.setText(result.namaLengkap)
            binding.tanggalLahirEditText.setText(result.tanggalLahir)
            binding.alamatEditText.setText(result.alamat)
        }
    }

}