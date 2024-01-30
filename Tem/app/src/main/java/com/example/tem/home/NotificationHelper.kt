package com.example.tem.home

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.tem.R

class NotificationHelper : ContextWrapper {

    val channelID = "Tem"
    val channelName = "Notification"
    var notificationManager: NotificationManager? = null
    var name: String = ""

    constructor(base: Context?, name: String?) : super(base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
            this.name = name!!
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(){
        val descriptionText = "유효기간"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, channelName, importance).apply {
            description = descriptionText
        }
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.setLightColor(R.color.green)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        // Register the channel with the system
        getManager()?.createNotificationChannel(channel)
    }

    fun getManager(): NotificationManager? {
        if(notificationManager == null){
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager
    }

    fun getChannelNotification(): NotificationCompat.Builder {
        return  NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("[$name] 의 교체 주기가 다가왔어요~")
            .setSmallIcon(R.drawable.tambourine_logo)
    }

//    with(NotificationManagerCompat.from(this)){
//        notify(0, Notification().build())
//    }

}