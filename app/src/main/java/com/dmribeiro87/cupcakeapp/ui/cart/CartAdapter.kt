package com.dmribeiro87.cupcakeapp.ui.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.ItemCartBinding
import com.dmribeiro87.cupcakeapp.databinding.ItemHomeBinding
import com.dmribeiro87.cupcakeapp.utils.twoDecimals
import com.dmribeiro87.model.Cupcake

class CartAdapter: RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cupcakeList = emptyList<Cupcake>()
    private var actionAdd: ((Cupcake) -> Unit)? = null
    private var actionRemove: ((Cupcake) -> Unit)? = null
    private var cupcakeQuantities: MutableMap<String, Int> = mutableMapOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, parent.context)
    }

    override fun getItemCount() = cupcakeList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cupcakeList[position])
    }

    fun setData(newCupcakes: List<Cupcake>) {
        cupcakeList = newCupcakes

        // Atualiza as quantidades com base na nova lista de cupcakes
        cupcakeQuantities.clear()
        newCupcakes.groupBy { it.productId }.forEach { (productId, cupcakes) ->
            cupcakeQuantities[productId] = cupcakes.size
        }
        notifyDataSetChanged()
    }

    fun setActionAdd(action: (Cupcake) -> Unit) {
        this.actionAdd = action
    }

    fun setActionRemove(action: (Cupcake) -> Unit) {
        this.actionRemove = action
    }

    inner class CartViewHolder(private val binding: ItemCartBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(cupcake: Cupcake) {
            binding.tvTitle.text = cupcake.name
            binding.tvDescription.text = cupcake.description
            binding.tvPrice.text = "R$ ${twoDecimals(cupcake.price)}"
            binding.tvWeight.text = "${cupcake.weight}g"

            // Define a quantidade inicial para o cupcake
            val quantity = cupcakeQuantities.getOrDefault(cupcake.productId, 0)
            binding.tvQuantity.text = quantity.toString()

            Glide.with(context)
                .load(cupcake.image)
                .placeholder(R.drawable.cupcake_chocolate_img)
                .into(binding.ivCupcake)


            binding.btPlus.setOnClickListener {
                actionAdd?.invoke(cupcake)
            }

            binding.btLess.setOnClickListener {
                actionRemove?.invoke(cupcake)
            }
        }
    }
}