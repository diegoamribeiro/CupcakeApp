package com.dmribeiro87.cupcakeapp.ui.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentCardBinding
import com.dmribeiro87.cupcakeapp.databinding.FragmentPixPaymentBinding
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import com.dmribeiro87.model.PaymentMethod
import com.dmribeiro87.model.PaymentType
import com.dmribeiro87.model.PixPayment
import org.koin.androidx.viewmodel.ext.android.viewModel


class PixPaymentFragment : Fragment() {

    private val binding: FragmentPixPaymentBinding by viewBinding()
    private val viewModel: PixViewModel by viewModel()
    private val orderId = "unique-order-of-the-galaxy"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.btCopy.setOnClickListener {
            // Atualiza o tipo de pagamento
            viewModel.updatePaymentType(orderId, PaymentType(
                PaymentMethod.PIX,
                pixPayment = PixPayment(binding.etEmv.text.toString())
            ))

            // Copia o texto para a área de transferência
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.etEmv.text.toString())
            clipboard.setPrimaryClip(clip)

            // Opcional: Mostrar uma mensagem curta para o usuário
            Toast.makeText(context, "Texto copiado para a área de transferência", Toast.LENGTH_SHORT).show()
        }
    }


}