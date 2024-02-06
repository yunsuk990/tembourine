package com.example.tem.home.shop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.tem.R
import com.example.tem.databinding.FragmentShopInternetBinding

class ShopInternetFragment : Fragment() {

    lateinit var binding: FragmentShopInternetBinding
    private var defaultUrl= "https://search.shopping.naver.com/search/all?query="

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopInternetBinding.inflate(layoutInflater)

        var title: String? = arguments?.getString("url", null).toString()
        var classNumber: Int? = arguments?.getInt("number", -1)
        if(classNumber == 0){
            defaultUrl = "http://www.coupang.com/np/search?component=&q=${title}&channel=user"
        }else if(classNumber == 1){
            defaultUrl += title
        }

        binding.shopWebview.settings.javaScriptEnabled = true
        binding.shopWebview.webViewClient = WebViewClient()
        binding.shopWebview.webChromeClient = WebChromeClient()
        binding.shopWebview.loadUrl(defaultUrl)

        binding.shopInternetBackIv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ShopFragment())
                .commit()
        }

        binding.shopInternetBookmark.setOnClickListener {
            AddBookmarkCustomDialog().show(requireActivity().supportFragmentManager, AddBookmarkCustomDialog.TAG)
        }

        return binding.root
    }
}