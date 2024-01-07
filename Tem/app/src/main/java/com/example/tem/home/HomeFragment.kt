package com.example.tem.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.tem.R
import com.example.tem.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),MyDialogListener {
    private lateinit var binding: FragmentHomeBinding
    private val data= mutableListOf<String>()
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