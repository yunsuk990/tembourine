package com.example.tem.home.shop
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tem.R
import com.example.tem.databinding.ItemSearchHistoryBinding
import com.example.tem.home.model.SearchData

class ShopSearchHistoryRVAdapter: RecyclerView.Adapter<ShopSearchHistoryRVAdapter.SearchItemViewHolder>() {

    private var searchHistoryList: ArrayList<SearchData> = ArrayList()

    interface MyItemClickListener {
        fun deleteItem(position: Int)
        fun onClick(position: Int)
    }
    private lateinit var mitemClickListener: MyItemClickListener

    fun setMyItemClickListener(myItemClickListener: MyItemClickListener){
        this.mitemClickListener = myItemClickListener
    }

    inner class SearchItemViewHolder(val binding: ItemSearchHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchItem: SearchData){
            binding.searchText.text = searchItem.searchText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemViewHolder(binding)
    }

    override fun getItemCount(): Int = searchHistoryList.size

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(searchHistoryList[position])
        holder.binding.deleteIv.setOnClickListener {
            mitemClickListener.deleteItem(position)
        }
        holder.itemView.setOnClickListener{
            mitemClickListener.onClick(position)
        }
    }

    //
    fun submitList(searchHistoryList: ArrayList<SearchData>){
        this.searchHistoryList = searchHistoryList
    }
}