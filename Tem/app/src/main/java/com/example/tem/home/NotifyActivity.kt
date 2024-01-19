package com.example.tem.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tem.R
import com.example.tem.databinding.ActivityNotifyBinding

class NotifyActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notifyBackIv.setOnClickListener {
            finish()
        }
    }
}