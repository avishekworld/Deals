package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation

import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.FragmentDealItemBinding
import com.target.targetcasestudy.ui.deals.viewmodel.DealVM
import life.avishekworld.domain.util.isNotNullAndNotEmpty
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DealItemFragment : Fragment() {

  private val viewModel : DealVM by sharedViewModel()
  private lateinit var binding : FragmentDealItemBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentDealItemBinding.inflate(layoutInflater, container, false)
    //TODO use data binding
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
      renderProcessingViewState(viewState.processingViewState)
      renderDealsDetails(viewState.dealDetailsViewState)
      renderErrorView(viewState.errorViewState)
    }
    viewModel.handleEvent(DealVM.DealsEvent.DealDetailsViewInit(arguments ?: Bundle()))
  }

  private fun renderDealsDetails(dealDetailsViewState : DealVM.DealDetailsViewState) {
    when(dealDetailsViewState) {
      is DealVM.DealDetailsViewState.Hide -> {}
      is DealVM.DealDetailsViewState.Show -> {
        dealDetailsViewState.product.let { product ->
          binding.itemTitleTextView.text = product.title
          binding.itemDealPriceTextView.text = product.salePrice?.displayString
          binding.itemRegularPriceTextView.text = product.regularPrice.displayString
          binding.itemDescriptionTextView.text = product.description
          when {
            product.imageUrl.isNotNullAndNotEmpty() -> {
              binding.itemImageView.load(product.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
              }
            }
            else -> {
              binding.itemImageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
          }
        }
      }
    }
  }

  private fun renderProcessingViewState(processingViewState: DealVM.ProcessingViewState) {
    when(processingViewState) {
      is DealVM.ProcessingViewState.Hide -> binding.processingView.visibility = View.GONE
      is DealVM.ProcessingViewState.Show -> binding.processingView.visibility = View.VISIBLE
    }
  }

  private fun renderErrorView(errorViewState: DealVM.ErrorViewState) {
    when (errorViewState) {
      is DealVM.ErrorViewState.Hide -> binding.errorView.visibility = View.GONE
      is DealVM.ErrorViewState.Show -> {
        binding.errorView.visibility = View.VISIBLE
        binding.errorTextView.text = errorViewState.errorText
      }
    }
  }

  companion object {
    const val PRODUCT_EXTRA = "product"
  }
}
