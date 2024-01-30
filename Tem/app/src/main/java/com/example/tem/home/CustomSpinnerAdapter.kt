package com.example.tem.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tem.R


class CustomSpinnerAdapter(
    private val context: Context,
    private val values: Array<String>
) : ArrayAdapter<String>(context, R.layout.custom_spinner_item, values) {

    interface MyItemClickListener {
        fun itemClick(position: Int)
    }

    private var myItemClickListener: MyItemClickListener? = null
    fun setMyItemClickListener(myItemClickListener: MyItemClickListener){
        this.myItemClickListener = myItemClickListener
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent, R.layout.custom_spinner_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent, R.layout.custom_spinner_selected)
    }

    private fun getCustomView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?,
        layoutResourceId: Int
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = convertView ?: inflater.inflate(layoutResourceId, parent, false)
        val label = row.findViewById<TextView>(R.id.spinnerText)
        label.text = values[position]
        myItemClickListener?.itemClick(position)
        // Customize as needed
        return row
    }
}