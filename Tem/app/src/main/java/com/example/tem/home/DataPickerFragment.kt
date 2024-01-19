package com.example.tem.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.tem.R
import java.util.Calendar

class DatePickerFragment : DialogFragment() {

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

        return DatePickerDialog(requireContext(), parentFragment as OnDateSetListener, year, month, day)
    }
}