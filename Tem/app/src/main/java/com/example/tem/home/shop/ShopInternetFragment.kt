package com.example.tem.home.shop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity
import com.example.tem.R
import com.example.tem.databinding.FragmentShopInternetBinding
import java.net.URISyntaxException

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
        if (classNumber == 0) {
            defaultUrl = "http://www.coupang.com/np/search?component=&q=${title}&channel=user"
        } else if (classNumber == 1) {
            defaultUrl += title
        }

        binding.shopWebview.settings.javaScriptEnabled = true
        var webViewClient = MyWebViewClient(requireContext())
        binding.shopWebview.settings.useWideViewPort = true
        binding.shopWebview.settings.loadWithOverviewMode = true
        binding.shopWebview.webViewClient = webViewClient
        binding.shopWebview.webChromeClient = WebChromeClient()
        binding.shopWebview.loadUrl(defaultUrl)


        binding.shopInternetBackIv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ShopFragment())
                .commit()
        }

        binding.shopInternetBookmark.setOnClickListener {
            AddBookmarkCustomDialog().show(
                requireActivity().supportFragmentManager,
                AddBookmarkCustomDialog.TAG
            )
        }

        return binding.root
    }

    private class MyWebViewClient(val context: Context): WebViewClient(){

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null && url.startsWith("intent://")) {
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    val existPackage =
                         context.packageManager.getLaunchIntentForPackage(intent.getPackage()!!)
                    if (existPackage != null) {
                        context.startActivity(intent)
                    } else {
                        val marketIntent = Intent(Intent.ACTION_VIEW)
                        marketIntent.data =
                            Uri.parse("market://details?id=" + intent.getPackage()!!)
                        context.startActivity(marketIntent)
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else if (url != null && url.startsWith("coupang://")) {
                try {
                    return true
                } catch (e: URISyntaxException) {
                    Log.d("intent", e.toString())
                }

            }
            view?.loadUrl(url!!)
            return false
        }

    }

}

