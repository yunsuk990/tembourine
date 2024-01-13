package com.example.tem.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.tem.R
import com.example.tem.databinding.FragmentHomeBinding
import com.example.tem.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val cycle= mutableListOf<String>()
    private var title:String?=null
    companion object{
        private const val ARG_TITLE = "title"

        fun newInstance(title: String): RegisterFragment {
            val fragment = RegisterFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }
    private fun registerFragment(fragment: Fragment, title: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            title=it.getString(ARG_TITLE)
            Log.d("위치",title.toString())
        }
        if(title!=null){
            binding.placeItem.text=title
        }
        cycle.add("7일")
        cycle.add("14일")
        cycle.add("30일")
        cycle.add("60일")
        binding.backBtn.setOnClickListener {
            registerFragment(HomeFragment(),"1")
        }
        return view
    }
}