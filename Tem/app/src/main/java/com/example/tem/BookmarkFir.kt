package com.example.tem

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tem.databinding.FragmentBookmarkFirBinding

class BookmarkFir : Fragment() {
    private lateinit var binding: FragmentBookmarkFirBinding
    lateinit var itemAdapter: ItemAdapter
    val datas = mutableListOf<ItemData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBookmarkFirBinding.inflate(inflater,container,false)
        val view = binding.root
        initRecycler()
        binding.itemRv.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
    private fun initRecycler() {
        itemAdapter = ItemAdapter(requireContext())
        binding.itemRv.adapter = itemAdapter


        datas.apply {
            add(ItemData(img = R.drawable.coupang, name = "수건"))
            add(ItemData(img = R.drawable.coupang, name = "화장지"))

            itemAdapter.datas = datas
            itemAdapter.notifyDataSetChanged()
            Log.d("chk",datas.size.toString())
            Log.d("ch",datas.first().name)

        }
    }
}