package com.example.tem

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tem.databinding.ActivityMainBinding
import com.example.tem.home.HomeFragment
import java.util.Calendar
import com.example.tem.home.BookmarkFragment
import com.example.tem.home.SettingFragment
import com.example.tem.home.shop.ShopFragment

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
        binding.shopTab.setOnClickListener { changeFragment(ShopFragment()) }
        binding.BookmarkTab.setOnClickListener { changeFragment(BookmarkFragment()) }
        binding.settingTab.setOnClickListener { changeFragment(SettingFragment()) }

    }
    private fun changeFragment(fragment: Fragment) {
        // 클릭된 탭에 해당하는 프래그먼트로 교체
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, fragment).commit()
    }

    override fun onBackPressed() {
        // 현재 화면이 HomeFragment가 아니면 HomeFragment로 이동
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_frm)
        if (currentFragment !is HomeFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        } else {
            super.onBackPressed()
        }
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, p1)
        c.set(Calendar.MINUTE, p2)
        c.set(Calendar.SECOND, 0)
//        startAlarm(c, "콜라",1)
    }

}