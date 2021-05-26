package com.target.targetcasestudy.ui.deals

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.DealListItemBinding
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.util.isNotNullAndNotEmpty

class DealItemAdapter : RecyclerView.Adapter<DealItemViewHolder>() {

  private var deals : Deals = Deals(emptyList())

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DealListItemBinding.inflate(inflater, parent, false)
    return DealItemViewHolder(binding)
  }

  override fun getItemCount(): Int {
    return deals.productList.size
  }

  override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
     deals.productList[position].let { product ->
       viewHolder.bind(product)
     }
  }

  fun handleDeals(deals : Deals) {
    this.deals = deals
    notifyDataSetChanged()
  }
}

class DealItemViewHolder(private val binding: DealListItemBinding) : RecyclerView.ViewHolder(binding.root) {
  private val titleTextView : TextView by lazy {
    binding.dealItemTitleTextView
  }

  private val priceTextView : TextView by lazy {
    binding.dealItemPriceTextView
  }

  private val aisleTextView : TextView by lazy {
    binding.dealItemAisleTextView
  }

  private val itemImageView : ImageView by lazy {
    binding.dealImageView
  }

  fun bind(product: Product) {
    titleTextView.text = product.title
    priceTextView.text = product.getSalePriceText()
    aisleTextView.text = product.aisle
    when {
      product.imageUrl.isNotNullAndNotEmpty() -> {
        itemImageView.load(product.imageUrl) {
          crossfade(true)
          placeholder(R.drawable.ic_launcher_foreground)
          transformations(CircleCropTransformation())
        }
      }
      else -> {
        itemImageView.setImageResource(R.drawable.ic_launcher_foreground)
      }
    }
  }
}