package com.example.tem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tem.databinding.FragmentBookmarkFirBinding
import com.example.tem.databinding.FragmentBookmarkSecBinding

class BookmarkSec : Fragment() {
    private lateinit var binding: FragmentBookmarkSecBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBookmarkSecBinding.inflate(inflater,container,false)
        var view=binding.root
        return view
    }
}