package com.example.tem.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.tem.MainActivity
import com.example.tem.R
import com.example.tem.Shop
import com.example.tem.databinding.FragmentShopBinding


class ShopFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    lateinit var binding: FragmentShopBinding
    private var defaultUrl = "https://shopping.naver.com/home"
    private var imageVal: Array<Int> = arrayOf(R.drawable.coopang, R.drawable.naver)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentShopBinding.inflate(layoutInflater)

        var shopdata: ArrayList<Shop> = ArrayList()
        var coopang = Shop()
        var naver = Shop()
        coopang.setImage(R.drawable.coopang)
        naver.setImage(R.drawable.naver)
        shopdata.add(coopang)
        shopdata.add(naver)

        binding.shopWebview.settings.javaScriptEnabled = true
        binding.shopWebview.webViewClient = WebViewClient()
        binding.shopWebview.webChromeClient = WebChromeClient()
        binding.shopWebview.loadUrl(defaultUrl)

        var customSpinnerImageAdapter = CustomSpinnerImageAdapter(requireActivity().baseContext, shopdata)
        binding.spinner.adapter = customSpinnerImageAdapter





        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

    override fun onNothingSelected(p0: AdapterView<*>?) {}


}