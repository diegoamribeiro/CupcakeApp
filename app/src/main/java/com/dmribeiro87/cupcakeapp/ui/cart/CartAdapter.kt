package com.dmribeiro87.cupcakeapp.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.ItemHomeBinding
import com.dmribeiro87.cupcakeapp.utils.twoDecimals
import com.dmribeiro87.model.Cupcake

class CartAdapter: RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cupcakeList = emptyList<Cupcake>()
    private var action: ((Cupcake) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, parent.context)
    }

    override fun getItemCount() = cupcakeList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cupcakeList[position], action)
    }

    fun setData(list: List<Cupcake>){
        this.cupcakeList = list
        notifyDataSetChanged()
        // Comments
    }

    fun setAction(action: (Cupcake) -> Unit) {
        this.action = action
    }

    inner class CartViewHolder(private val binding: ItemHomeBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root){

        fun bind(cupcake: Cupcake, action: ((Cupcake) -> Unit?)?){
            binding.tvTitle.text = cupcake.name
            binding.tvDescription.text = cupcake.description
            binding.tvPrice.text = "R$ ${twoDecimals(cupcake.price)}"
            binding.tvWeight.text = "${cupcake.weight}g"
            Glide.with(context)
                .load(cupcake.image)
                .placeholder(R.drawable.cupcake_chocolate_img)
                .into(binding.ivCupcake)

            action?.let { currentCupcake ->
                binding.root.setOnClickListener {
                    currentCupcake(cupcake)
                }
            }
        }
    }
}