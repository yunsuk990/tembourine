package com.example.tem.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tem.R
import com.example.tem.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accountBackIv.setOnClickListener {
            finish()
        }

        binding.accountLogoutCv.setOnClickListener{
            LogoutCustomDialog().show(supportFragmentManager, LogoutCustomDialog.TAG)
        }

        binding.accountQuitCv.setOnClickListener{
            SignOutCustomDialog().show(supportFragmentManager, SignOutCustomDialog.TAG)
        }
    }
}