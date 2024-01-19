package com.example.tem.home
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.DialogFragment
import com.example.tem.databinding.DialogSignoutBinding
import com.kakao.sdk.user.UserApiClient

class SignOutCustomDialog: DialogFragment() {

    lateinit var binding: DialogSignoutBinding

    companion object {
        const val TAG = "SignOutCustomDialog"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSignoutBinding.inflate(layoutInflater)

        binding.dialogQuitBt.setOnClickListener {
            dismiss()
        }

        binding.dialogSubmitBt.setOnClickListener {
            signOut()
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
        dialog?.window?.attributes = params as LayoutParams

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun signOut(){

    }
}