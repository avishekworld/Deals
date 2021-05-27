package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.target.targetcasestudy.databinding.FragmentDealListBinding
import com.target.targetcasestudy.ui.deals.viewmodel.DealVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DealListFragment : Fragment() {

  private val viewModel : DealVM by sharedViewModel()
  private lateinit var binding : FragmentDealListBinding

  private val adapter by lazy {
    DealItemAdapter { product -> viewModel.handleEvent(DealVM.DealsEvent.DealListItemSelected(product))  }
  }

  private val layoutManager by lazy {
    LinearLayoutManager(requireContext())
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentDealListBinding.inflate(inflater, container, false).apply {
      recyclerView.adapter = adapter
      recyclerView.layoutManager = layoutManager
    }
    //TODO use data binding
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.viewState.observe(viewLifecycleOwner) {
      renderProcessingViewState(it.processingViewState)
      renderDealListViewState(it.dealListViewState)
    }
    viewModel.handleEvent(DealVM.DealsEvent.DealListViewInit)
  }

  private fun renderProcessingViewState(processingViewState: DealVM.ProcessingViewState) {
    when(processingViewState) {
      is DealVM.ProcessingViewState.Hide -> binding.processingView.visibility = View.GONE
      is DealVM.ProcessingViewState.Show -> binding.processingView.visibility = View.VISIBLE
    }
  }

  private fun renderDealListViewState(dealListViewState: DealVM.DealListViewState) {
    when(dealListViewState) {
      is DealVM.DealListViewState.Hide -> binding.contentView.visibility = View.GONE
      is DealVM.DealListViewState.Show -> {
        binding.contentView.visibility = View.VISIBLE
        adapter.handleDeals(dealListViewState.deals)
      }
    }
  }
}
