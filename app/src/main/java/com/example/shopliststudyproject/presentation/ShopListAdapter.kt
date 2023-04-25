package com.example.shopliststudyproject.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopliststudyproject.R
import com.example.shopliststudyproject.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
       val layout = when (viewType){
           VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
           VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
           else -> throw RuntimeException("Unknown view type: $viewType")
       }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = currentList[position]

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        viewHolder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }



    }



    override fun getItemViewType(position: Int): Int {
        val viewType = if (getItem(position).enabled){
            VIEW_TYPE_ENABLED
        } else{
            VIEW_TYPE_DISABLED
        }
        return viewType
    }


    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }


    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 2

        const val MAX_POOL_SIZE = 15
    }

}