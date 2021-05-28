package com.target.targetcasestudy.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.target.targetcasestudy.databinding.DialogPaymentBinding
import com.target.targetcasestudy.ui.deals.viewmodel.DealVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Dialog that displays a minimal credit card entry form.
 *
 * Your task here is to enable the "submit" button when the credit card number is valid and
 * disable the button when the credit card number is not valid.
 *
 * You do not need to input any of your actual credit card information. See `Validators.kt` for
 * info to help you get fake credit card numbers.
 *
 * You do not need to make any changes to the layout of this screen (though you are welcome to do
 * so if you wish).
 */
class PaymentDialogFragment : DialogFragment() {

  private val viewModel : DealVM by sharedViewModel()
  private lateinit var binding : DialogPaymentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DialogPaymentBinding.inflate(inflater, container, false).also {
      it.lifecycleOwner = this@PaymentDialogFragment
      it.vm = viewModel
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
      renderPaymentViewState(viewState.dealsPaymentViewState)
    }
    binding.submit.setOnClickListener {
      viewModel.handleEvent(DealVM.DealsEvent.PaymentSubmitClicked(binding.cardNumber.text.toString()))
    }

    binding.cardNumber.doAfterTextChanged { editable ->
      viewModel.handleEvent(DealVM.DealsEvent.CardNumberEntered(editable.toString()))
    }

  }

  private fun renderPaymentViewState(state : DealVM.DealsPaymentViewState) {
    when(state) {
      is DealVM.DealsPaymentViewState.Init -> {}
      is DealVM.DealsPaymentViewState.Show -> {}
      is DealVM.DealsPaymentViewState.Hide -> dismiss()
      is DealVM.DealsPaymentViewState.EnableSubmit -> binding.submit.isEnabled = true
      is DealVM.DealsPaymentViewState.DisableSubmit -> binding.submit.isEnabled = false
    }
  }

}