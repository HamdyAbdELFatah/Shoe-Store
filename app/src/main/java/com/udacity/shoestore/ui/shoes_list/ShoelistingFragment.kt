package com.udacity.shoestore.ui.shoes_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoelistingBinding
import com.udacity.shoestore.models.Shoe

class ShoelistingFragment : Fragment() {
    private val TAG = "ShoelistingFragment"
    private lateinit var binding: FragmentShoelistingBinding

    private val viewModel: ShoelistingViewModel by activityViewModels()
    private val args: ShoelistingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_shoelisting,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.shoesList.observe(viewLifecycleOwner) { shoes ->
            binding.listShoes.removeAllViews()
            shoes.forEach { shoe ->
                addView(shoe.name)
            }
        }

        binding.addShoe.setOnClickListener {
            it.findNavController()
                .navigate(ShoelistingFragmentDirections.actionShoelistingFragmentToDetailsFragment())
            //viewModel.addShoe(Shoe("hamdy", "1".toDouble(), "company", "description", listOf("")))
        }
        val shoe = args.shoeItem
        Log.d(TAG, "onViewCreated:   $shoe")
        if (shoe != null) {
            viewModel.addShoe(shoe)
            arguments!!.clear()
        }



        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logout -> {
                        findNavController().navigate(ShoelistingFragmentDirections.actionShoelistingFragmentToLoginFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun addView(name: String) {
        Log.d(TAG, "addView: onViewCreated ")
        val shoe = TextView(context)
        shoe.text = name
        shoe.textSize = 25f
        shoe.setTextColor(context!!.getColor(R.color.colorPrimary))
        shoe.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(4, 16, 4, 0)
        binding.listShoes.addView(shoe, params)
    }


}