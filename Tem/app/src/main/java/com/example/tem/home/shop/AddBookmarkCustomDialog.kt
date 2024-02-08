package com.example.tem.home.shop

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tem.databinding.MydialogBinding

class AddBookmarkCustomDialog: DialogFragment() {

    lateinit var binding: MydialogBinding

    companion object {
        const val TAG = "AddBookmarkCustomDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MydialogBinding.inflate(layoutInflater)
        binding.top.text = "북마크명 설정"
        binding.plusThing.setText("상품명을 입력해주세요")

        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.okay.setOnClickListener {

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}