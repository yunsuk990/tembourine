package com.example.tem.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tem.BookmarkFir
import com.example.tem.BookmarkSec
import com.example.tem.ItemAdapter
import com.example.tem.ItemData
import com.example.tem.R
import com.example.tem.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding
    lateinit var itemAdapter: ItemAdapter
    val datas = mutableListOf<ItemData>()
    private fun changeFragment(fragment: Fragment, title: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }
    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        // 화면에 표시할 Fragment 리스트
        val pageList = listOf(
            BookmarkFir()
        )

        override fun getItemCount(): Int {
            return pageList.size
        }

        override fun createFragment(position: Int): Fragment {
            return pageList[position]
        }

    }
    private val pagerAdpater by lazy {
        ViewPagerAdapter(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBookmarkBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backBtn.setOnClickListener {
            changeFragment(HomeFragment(),"1")
        }
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