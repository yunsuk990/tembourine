package com.example.tem

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.tem.databinding.DialogPushBinding
import com.example.tem.home.SplashActivity

class PushPermissionDialogFragment: DialogFragment() {

    lateinit var binding: DialogPushBinding
    companion object {
        const val TAG = "PushPermissionDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPushBinding.inflate(layoutInflater)

        binding.pushCancelBt.setOnClickListener {
            requireActivity().finishAffinity()
        }

        binding.pushAdmitBt.setOnClickListener {
            gotoSetting()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT

        dialog?.window?.setLayout(width, height)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //외부 클릭 막기
        dialog?.setCancelable(false)
//        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    // Android 설정창으로 이동
    fun gotoSetting(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }
    }
}