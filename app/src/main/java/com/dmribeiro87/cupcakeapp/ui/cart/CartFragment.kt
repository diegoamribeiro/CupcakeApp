package com.dmribeiro87.cupcakeapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmribeiro87.cupcakeapp.databinding.FragmentCartBinding
import com.dmribeiro87.cupcakeapp.utils.viewBinding

class CartFragment : Fragment() {

    private val binding: FragmentCartBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}