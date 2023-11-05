package com.dmribeiro87.cupcakeapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dmribeiro87.cupcakeapp.MainActivity
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentDetailsBinding
import com.dmribeiro87.cupcakeapp.utils.twoDecimals
import com.dmribeiro87.cupcakeapp.utils.viewBinding


class DetailsFragment : Fragment() {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.white)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(
            resources.getDrawable(
                R.color.white,
                resources.newTheme()
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        val cupcake = args.selectedCupcake
        binding.tvFlavour.text = cupcake.flavor
        binding.tvDescription.text = cupcake.description
        binding.tvPrice.text = "R$ ${twoDecimals(cupcake.price)}"

        Glide.with(this@DetailsFragment)
            .load(cupcake.image)
            .placeholder(R.drawable.cupcake_chocolate_img)
            .into(binding.ivCupcake)
    }




}