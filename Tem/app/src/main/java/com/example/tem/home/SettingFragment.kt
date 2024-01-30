package com.example.tem.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tem.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)

        binding.settingProfileCv.setOnClickListener{
            startActivity(Intent(context, AccountActivity::class.java))
        }

        binding.settingNotifyCv.setOnClickListener{
            startActivity(Intent(context, NotifyActivity::class.java))
        }

        return binding.root
    }
}