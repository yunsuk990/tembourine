package com.example.tem.home

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar

class AlertReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        var name = p1!!.getStringExtra("name")
        var repeat: Int = p1!!.getIntExtra("repeat", 0)

//        val calendar: Calendar = Calendar.getInstance()
//        calendar.set(Calendar.YEAR, Calendar.YEAR)
//        calendar.set(Calendar.MONTH, Calendar.MONTH)
//        calendar.set(Calendar.DATE, Calendar.DATE + repeat)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            context, id, intent,
//            //PendingIntent.FLAG_NO_CREATE
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        //알람매니저에 다음 알람을 예약시킵니다.
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            pendingIntent
//        )

        var notificationHelper = NotificationHelper(p0, name)
        var nb = notificationHelper.getChannelNotification()
        notificationHelper.getManager()?.notify(1, nb.build())
    }
}