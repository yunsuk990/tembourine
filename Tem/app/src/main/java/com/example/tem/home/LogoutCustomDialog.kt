package com.example.tem.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.tem.databinding.DialogLogoutBinding
import com.kakao.sdk.user.UserApiClient
import kotlin.math.log

class LogoutCustomDialog: DialogFragment() {

    lateinit var binding: DialogLogoutBinding

    companion object {
        const val TAG = "LogoutCustomDialog"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLogoutBinding.inflate(layoutInflater)

        binding.dialogQuitBt.setOnClickListener {
            dismiss()
        }

        binding.dialogSubmitBt.setOnClickListener {
            logOut()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val deviceWidth = size.x
        val deviceHeght = size.y
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.95).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun logOut(){
        UserApiClient.instance.logout { error ->

        }
        startActivity(Intent(context, StartActivity::class.java))
    }
}