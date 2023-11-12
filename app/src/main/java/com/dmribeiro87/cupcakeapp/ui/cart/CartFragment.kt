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
    private var orderId = ""

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
        viewModel.orders.observe(viewLifecycleOwner) { ordersList ->
            // Primeiro, coletamos todos os cupcakes de todos os pedidos em uma única lista
            val allCupcakes = ordersList.flatMap {
                it.list
            }

            // Em seguida, agrupamos esses cupcakes pelo productId e pegamos o primeiro de cada grupo
            val uniqueCupcakes = allCupcakes
                .groupBy { it.productId }
                .map { (_, cupcakes) -> cupcakes.first() }

            // Agora, uniqueCupcakes contém apenas um cupcake de cada tipo
            cartAdapter.setData(uniqueCupcakes)

            if (ordersList.isNotEmpty()) {
                orderId = ordersList.first().orderId
            }

            // Atualiza a visibilidade dos elementos de UI
            if (ordersList.isNullOrEmpty()) {
                binding.ivEmptyCart.visibility = View.VISIBLE
                binding.tvEmptyCart.visibility = View.VISIBLE
            } else {
                binding.ivEmptyCart.visibility = View.GONE
                binding.tvEmptyCart.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter().apply {
            setActionAdd {
                Log.d("***Add", it.flavor)
                viewModel.initializeOrAddCupcakeToSelection(it)
                viewModel.createOrderForCheckout()
            }
            setActionRemove { cupcake ->
                viewModel.removeCupcakes(cupcake = cupcake, orderId = orderId)
                Log.d("***Remove", cupcake.flavor)
            }
        }
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = cartAdapter
    }

}