package com.example.tem.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.tem.R
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var customArgument: Int = 0
    companion object {
        const val ARG_CUSTOM_ARGUMENT = "arg_custom_argument"

        fun newInstance(customArgument: Int): DatePickerFragment {
            val fragment = DatePickerFragment()
            val args = Bundle()
            args.putInt(ARG_CUSTOM_ARGUMENT, customArgument)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            customArgument = it.getInt(ARG_CUSTOM_ARGUMENT, 0)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)

        // 호출한 Fragment의 ItemBuy TextView를 찾아서 텍스트를 설정합니다.
        val parentFragment = parentFragment
        if (parentFragment is Fragment) {
            var itemBuyTextView :TextView?
            if(customArgument ==1){
                itemBuyTextView = parentFragment.view?.findViewById<TextView>(R.id.item_buy)
            }
            else{
                itemBuyTextView = parentFragment.view?.findViewById<TextView>(R.id.item_date)
            }
            itemBuyTextView?.text = selectedDate
        }
    }
}