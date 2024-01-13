package com.example.tem.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tem.R
import com.example.tem.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),MyDialogListener {
    private lateinit var binding: FragmentHomeBinding
    private val data= mutableListOf<String>()
    private val myItemList = mutableListOf<MyItem>()
    private fun registerFragment(fragment: Fragment, title: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
        (fragment.view?.findViewById<TextView>(R.id.place_item))?.text = title
        Log.d("체크용",(fragment.view?.findViewById<TextView>(R.id.place_item))?.text.toString())
        Log.d("체",title)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val listView: ListView = view.findViewById(R.id.my_rv)
        binding.showDetail.setOnClickListener {
            showDetail()
        }
        // 샘플 데이터 생성 (원하는 데이터로 변경)
        data.add("부엌")
        data.add("서랍")

        // ArrayAdapter를 사용하여 데이터를 ListView에 바인딩
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_txt_item, data)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem=data[position]
            binding.curSpace.text=selectedItem
            hideDetail()
        }
        binding.add.setOnClickListener {
            val myDialog=MyDialog(this)
            myDialog.show()
        }
        for (i in 0 until myItemList.size) {
            val currentItem = myItemList[i]
            val position=i+1
            val hvItemLayout = view.findViewById<RelativeLayout>(resources.getIdentifier("hv_item$position", "id", context?.packageName))
            val psItemLayout=view.findViewById<RelativeLayout>(resources.getIdentifier("plus_item$position", "id", context?.packageName))
            val nameItem=view.findViewById<TextView>(resources.getIdentifier("name_item$position", "id", context?.packageName))
            val dateItem=view.findViewById<TextView>(resources.getIdentifier("data_item$position", "id", context?.packageName))
            val cycleItem=view.findViewById<TextView>(resources.getIdentifier("cycle_item$position", "id", context?.packageName))
            hvItemLayout.visibility=View.VISIBLE
            psItemLayout.visibility=View.GONE

            // currentItem을 hvItemLayout에 바인딩하는 코드 작성
            nameItem.text = currentItem.name
            dateItem.text=currentItem.deadline
            cycleItem.text=currentItem.cycle
        }
        binding.plusItem1.setOnClickListener {
            val registerFragment=RegisterFragment.newInstance(binding.curSpace.text.toString())
            registerFragment(registerFragment,"1")
        }
        binding.plusItem2.setOnClickListener {
            val registerFragment=RegisterFragment.newInstance(binding.curSpace.text.toString())
            registerFragment(registerFragment,"1")
        }
        binding.plusItem3.setOnClickListener {
            val registerFragment=RegisterFragment.newInstance(binding.curSpace.text.toString())
            registerFragment(registerFragment,"1")
        }
        binding.plusItem4.setOnClickListener {
            val registerFragment=RegisterFragment.newInstance(binding.curSpace.text.toString())
            registerFragment(registerFragment,"1")
        }


        return view
    }
    override fun onPositiveButtonClick(content: String) {
        data.add(content)
    }

    private fun showDetail(){
        binding.myRv.visibility=View.VISIBLE
        binding.add.visibility=View.VISIBLE
        binding.curSpace.visibility=View.INVISIBLE
    }
    private fun hideDetail(){
        binding.myRv.visibility=View.INVISIBLE
        binding.add.visibility=View.INVISIBLE
        binding.curSpace.visibility=View.VISIBLE
    }

}