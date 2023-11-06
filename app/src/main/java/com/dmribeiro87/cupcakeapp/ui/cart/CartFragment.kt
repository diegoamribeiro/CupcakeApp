package com.dmribeiro87.cupcakeapp.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dmribeiro87.cupcakeapp.databinding.FragmentCartBinding
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private val binding: FragmentCartBinding by viewBinding()
    private val viewModel: CartViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadOrders()
        addObserver()
    }


    private fun addObserver() {
        viewModel.orders.observe(viewLifecycleOwner){ ordersList ->
            Log.d("***CartOrder", ordersList.toString())
            Log.d("***CartSize", ordersList.size.toString())
        }
    }

}