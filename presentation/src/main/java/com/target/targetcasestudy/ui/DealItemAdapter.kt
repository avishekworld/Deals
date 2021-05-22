package com.target.targetcasestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.StaticData

class DealItemAdapter : RecyclerView.Adapter<DealItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.deal_list_item, parent, false)
    return DealItemViewHolder(view)
  }

  override fun getItemCount(): Int {
    return StaticData.deals.size
  }

  override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
    val item = StaticData.deals[position]
    viewHolder.itemView.findViewById<TextView>(R.id.deal_list_item_title).text = item.title
    viewHolder.itemView.findViewById<TextView>(R.id.deal_list_item_price).text = item.price
  }
}

class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}