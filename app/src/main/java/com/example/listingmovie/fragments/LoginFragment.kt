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
import com.example.listingmovie.databinding.FragmentLoginBinding
import com.example.listingmovie.viewmodel.LoginViewModel
import com.example.listingmovie.viewmodel.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    var email = ""
    var password = ""
    var isLogged = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setObserver()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            if (binding.emailEditText.text.toString() == email && binding.passwordEditText.text.toString() == password) {
                viewModel.saveLogin(true)
                toHome()
            } else {
                Toast.makeText(requireContext(), "Email atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setObserver() {
        viewModel.apply {
            getIsLogged().observe(viewLifecycleOwner) { result ->
                if (result) {
                    toHome()
                }
            }

            getUser().observe(viewLifecycleOwner) { result ->
                email = result.email
                password = result.password
            }
        }
    }

    private fun toHome() {
        findNavController().navigate(
            R.id.action_loginFragment_to_homeFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
        )
    }

}