package com.example.tem.home

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlertReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        var name = p1!!.getStringExtra("name")

        var notificationHelper = NotificationHelper(p0, name)
        var nb = notificationHelper.getChannelNotification()
        notificationHelper.getManager()?.notify(1, nb.build())
    }
}