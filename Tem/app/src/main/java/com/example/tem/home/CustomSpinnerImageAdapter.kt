package com.example.tem.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tem.R
import com.example.tem.Shop


class CustomSpinnerImageAdapter() : BaseAdapter() {

    var context: Context?  = null
    var values: List<Shop>? = null


    constructor(context: Context, values: List<Shop>) : this() {
        this.context = context
        this.values = values
    }

    override fun getCount(): Int = values!!.size

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_image_item, null)
        val image = view.findViewById<ImageView>(R.id.spinnerImage)
        image.setImageResource(values!!.get(p0).getImage()!!)
        return view
    }
}