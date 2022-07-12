package com.udacity.shoestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import java.util.*

class LoginFragment : Fragment() {
    private val email = "hamdy@gmail.com"
    private val password = "123"
    private lateinit var binding: FragmentLoginBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            if (isLoginSuccessful()) {
                it.findNavController()
                    .navigate(LoginFragmentDirections.actionLoginFragmentToOnboardingFragment())

            } else if (isValid())
                Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_SHORT).show()
        }
        binding.createButton.setOnClickListener {
            it.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToOnboardingFragment())
        }


    }

    private fun isValid(): Boolean {
        val email = binding.email.editText?.text.toString().lowercase(Locale.getDefault()).trim()
        val password =
            binding.password.editText?.text.toString().lowercase(Locale.getDefault()).trim()
        var valid = true
        if (email.isBlank()) {
            binding.email.error = "Email Can't be blank"
            valid = false
        }
        if (password.isBlank()) {
            binding.password.error = "Password Can't be blank"
            valid = false
        }
        return valid
    }

    private fun isLoginSuccessful(): Boolean {
        val email = binding.email.editText?.text.toString().lowercase(Locale.getDefault()).trim()
        val password =
            binding.password.editText?.text.toString().lowercase(Locale.getDefault()).trim()

        if (isValid()) {
            if (this.email == email && this.password == password) {
                return true
            }
            return false
        }
        return false
    }

}