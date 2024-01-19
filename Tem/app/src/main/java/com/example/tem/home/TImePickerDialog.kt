package com.example.tem.home

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.Calendar

class TImePickerDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c= Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener, hour, minute, android.text.format.DateFormat.is24HourFormat(activity))


    }
}