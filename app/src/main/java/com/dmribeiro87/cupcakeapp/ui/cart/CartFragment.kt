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
import com.dmribeiro87.cupcakeapp.utils.twoDecimals
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
            // Define o orderId a partir do primeiro pedido da lista, se existir
            orderId = ordersList.firstOrNull()?.orderId.orEmpty()

            // Coleta todos os cupcakes de todos os pedidos em uma única lista
            val allCupcakes = ordersList.flatMap { it.list }

            // Calcula a quantidade total e o preço total
            val totalQuantity = allCupcakes.sumBy { 1 } // Ou allCupcakes.size
            val totalPrice = allCupcakes.sumByDouble { it.price }

            //binding.tvQuantity.text = totalQuantity.toString()
            binding.tvPrice.text = "R$ ${twoDecimals(totalPrice)}"

            // Agrupa os cupcakes pelo sabor e pega um de cada sabor
            val uniqueCupcakes = allCupcakes.groupBy { it.flavor }
                .map { (_, cupcakes) -> cupcakes.first() }

            // Atualiza o adaptador com a lista de cupcakes únicos
            cartAdapter.setData(uniqueCupcakes)

            // Prepara e envia a contagem de cada sabor para o adaptador
            val cupcakeQuantities = allCupcakes.groupBy { it.flavor }
                .mapValues { (_, cupcakes) -> cupcakes.size }
            cartAdapter.updateQuantities(cupcakeQuantities)

            // Atualiza a visibilidade dos elementos de UI
            updateCartVisibility(ordersList.isEmpty())
        }
    }

    private fun updateCartVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            binding.ivEmptyCart.visibility = View.VISIBLE
            binding.tvEmptyCart.visibility = View.VISIBLE
        } else {
            binding.ivEmptyCart.visibility = View.GONE
            binding.tvEmptyCart.visibility = View.GONE
        }
    }


    private fun setupRecyclerView() {
        cartAdapter = CartAdapter().apply {
            setActionAdd {cupcake ->
                Log.d("***Add", cupcake.flavor)
                viewModel.addCupcakeToOrder(cupcake)
            }
            setActionRemove { cupcake ->
                Log.d("***Remove", cupcake.flavor)
                viewModel.removeCupcakeFromOrder(cupcake)
            }
        }
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = cartAdapter
    }

}