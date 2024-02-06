package com.example.tem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAdapter(private val context: Context) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var datas = mutableListOf<ItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.txt_rv)
        private val imgProfile: ImageView = itemView.findViewById(R.id.img_rv)

        fun bind(item: ItemData) {
            txtName.text = item.name
            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }
}