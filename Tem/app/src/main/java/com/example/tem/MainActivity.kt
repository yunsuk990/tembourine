package com.example.tem

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.tem.databinding.ActivityMainBinding
import com.example.tem.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}