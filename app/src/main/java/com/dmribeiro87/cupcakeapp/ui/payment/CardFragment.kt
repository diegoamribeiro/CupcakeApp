package com.dmribeiro87.cupcakeapp.ui.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentCardBinding
import com.dmribeiro87.cupcakeapp.databinding.FragmentCartBinding
import com.dmribeiro87.cupcakeapp.ui.cart.CartViewModel
import com.dmribeiro87.cupcakeapp.utils.formatCardExpiryDate
import com.dmribeiro87.cupcakeapp.utils.formatCreditCardNumber
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import com.dmribeiro87.model.CardPayment
import com.dmribeiro87.model.PaymentMethod
import com.dmribeiro87.model.PaymentType
import org.koin.androidx.viewmodel.ext.android.viewModel


class CardFragment : Fragment() {

    private val binding: FragmentCardBinding by viewBinding()
    val orderId = "unique-order-of-the-galaxy"
    private val viewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
        setListeners()
    }

    // Incomplete

    private fun addObserver() {
        binding.edtNumberCard.addTextChangedListener(object : TextWatcher {
            var isUpdating = false

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                isUpdating = true
                val formatted = formatCreditCardNumber(s.toString())
                s?.replace(0, s.length, formatted)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Não é necessário para esta implementação
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Não é necessário para esta implementação
            }
        })

        binding.edtDateCard.addTextChangedListener(object : TextWatcher {
            var isUpdating = false

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                isUpdating = true
                val formatted = formatCardExpiryDate(s.toString())
                s?.replace(0, s.length, formatted)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Não é necessário para esta implementação
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Não é necessário para esta implementação
            }
        })

    }

    private fun setListeners() {


        binding.btFinish.setOnClickListener {
            if (isValid(
                    binding.edtNameCard.text.toString(),
                    binding.edtNumberCard.text.toString(),
                    binding.edtDateCard.text.toString(),
                    binding.edtCvcCard.text.toString()
                )
            ) {
                viewModel.updatePaymentType(
                    orderId,
                    PaymentType(
                        PaymentMethod.CARD,
                        cardPayment = CardPayment(
                            binding.edtNameCard.text.toString(),
                            binding.edtNumberCard.text.toString(),
                            binding.edtDateCard.text.toString(),
                            binding.edtCvcCard.text.toString()
                        )
                    )
                )
            }else{
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun isValid(
        name: String,
        cardNumber: String,
        dueDate: String,
        cvc: String,
    ): Boolean {

        if (
            name.isNullOrBlank() ||
            cardNumber.isNullOrBlank() ||
            dueDate.isNullOrBlank() ||
            cvc.isNullOrBlank()
        ) {
            return false
        }

        return true
    }


}