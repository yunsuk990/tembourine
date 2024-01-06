package com.example.tem.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tem.databinding.MydialogBinding

class MyDialog(private val HomeFragment:Fragment) {
    private lateinit var binding: MydialogBinding
    private val dlg=Dialog(HomeFragment.requireActivity())

    fun show(){
        binding = MydialogBinding.inflate(HomeFragment.layoutInflater)

        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
          //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        val layoutParams = dlg.window?.attributes
        layoutParams?.dimAmount = 0.7f
        dlg.window?.attributes = layoutParams
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //ok 버튼 동작
        binding.okay.setOnClickListener {

            val inputText=binding.plusThing.text.toString()
            (HomeFragment as MyDialogListener).onPositiveButtonClick(inputText)

            dlg.dismiss()
        }


        dlg.show()
    }
}