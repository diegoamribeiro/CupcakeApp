package com.dmribeiro87.cupcakeapp.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmribeiro87.cupcakeapp.databinding.FragmentCartBinding
import com.dmribeiro87.cupcakeapp.ui.home.HomeAdapter
import com.dmribeiro87.cupcakeapp.ui.home.HomeFragmentDirections
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import com.dmribeiro87.model.Cupcake
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private val binding: FragmentCartBinding by viewBinding()
    private val viewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadOrders()
        setupRecyclerView()
        addObserver()
    }


    private fun addObserver() {
        val listCupcake = mutableListOf<Cupcake>()
        viewModel.orders.observe(viewLifecycleOwner){ ordersList ->
            viewModel.orders.observe(viewLifecycleOwner){ list ->
                list.map {
                   listCupcake.addAll(it.list)
                }
            }
            cartAdapter.setData(listCupcake)
            if (ordersList.isNullOrEmpty()){
                binding.ivEmptyCart.visibility = View.VISIBLE
                binding.tvEmptyCart.visibility = View.VISIBLE
            }else{
                binding.ivEmptyCart.visibility = View.GONE
                binding.tvEmptyCart.visibility = View.GONE
            }
        }
    }


    private fun setupRecyclerView() {
        context.let { context ->
            cartAdapter = CartAdapter()
            binding.rvList.layoutManager = LinearLayoutManager(context)
            binding.rvList.adapter = cartAdapter
        }
    }

}