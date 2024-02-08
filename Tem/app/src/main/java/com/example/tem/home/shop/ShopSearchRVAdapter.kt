package com.example.tem.home.shop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tem.databinding.ItemShopSearchRankBinding

class ShopSearchRVAdapter(var items: ArrayList<String>): RecyclerView.Adapter<ShopSearchRVAdapter.ViewHolder>() {


    interface MyItemClickListener {
        fun onClick(title: String)
    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(myItemClickListener: MyItemClickListener){
        this.myItemClickListener = myItemClickListener
    }

    inner class ViewHolder(val binding: ItemShopSearchRankBinding): RecyclerView.ViewHolder(binding.root) {}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemShopSearchRankBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemIdTv.text = (position+1).toString() + "."
        holder.binding.itemInfoTv.text = items[position]
        holder.binding.itemShopIv.setOnClickListener {
            myItemClickListener.onClick(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}