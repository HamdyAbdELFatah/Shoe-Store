package com.udacity.shoestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentDetailsBinding
import com.udacity.shoestore.models.Shoe

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val shoe = Shoe("", "", "", "", listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_details,
                container,
                false
            )
        binding.shoe = shoe
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            if (isValid(shoe.name, shoe.size, shoe.company, shoe.description)) {
                val action =
                    DetailsFragmentDirections.actionDetailsFragmentToShoelistingFragment(
                        shoeItem = shoe,
                    )
                findNavController().navigate(action)
            }
        }
        binding.cancelButton.setOnClickListener {
            it.findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToShoelistingFragment(
                    shoeItem = null,
                )
            )
        }
    }

    private fun isValid(
        name: String,
        size: String,
        company: String,
        description: String,
    ): Boolean {
        var valid = true
        if (name.isNullOrBlank()) {
            binding.name.error = "Name Can't be blank"
            valid = false
        }
        if (size.isNullOrBlank()) {
            binding.size.error = "Size Can't be blank"
            valid = false
        }
        if (company.isNullOrBlank()) {
            binding.company.error = "Company Can't be blank"
            valid = false
        }
        if (description.isNullOrBlank()) {
            binding.description.error = "Description Can't be blank"
            valid = false
        }
        return valid
    }

}