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
import com.example.listingmovie.databinding.FragmentRegisterBinding
import com.example.listingmovie.model.User
import com.example.listingmovie.viewmodel.RegisterViewModel
import com.example.listingmovie.viewmodel.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDaftar.setOnClickListener {
            val password = binding.passwordEditText.text.toString()
            val passwordConfirm = binding.passwordConfirmEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if(password.isBlank() || passwordConfirm.isBlank() || username.isBlank() || email.isBlank()){
                Toast.makeText(requireContext(), "Semua harus diisi", Toast.LENGTH_SHORT).show()
            }else{
                if(password != passwordConfirm){
                    Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
                }else{
                    val user = User(
                        binding.usernameEditText.text.toString(),
                        binding.emailEditText.text.toString(),
                        password,
                        "",
                        "",
                        ""
                    )

                    viewModel.saveUser(user)

                    Toast.makeText(requireContext(), "Berhasil daftar, silahkan login", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(
                        R.id.action_registerFragment_to_loginFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                    )
                }
            }
        }
    }

}