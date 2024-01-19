package com.example.tem

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.tem.databinding.ActivityMainBinding
import com.example.tem.home.AlertReceiver
import com.example.tem.home.HomeFragment
import java.util.Calendar

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    fun startAlarm(c: Calendar, name: String, repeat: Int?) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        intent.putExtra("name", name)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        if (repeat != null) {
//            val repeatInterval = 10000L * repeat
            val repeatInterval = AlarmManager.INTERVAL_DAY * repeat
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + repeatInterval, repeatInterval,
                pendingIntent
            )
        } else {

            // 설정한 시간이 현재 시간 전이라면 날짜에 +1
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1)
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        }
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, p1)
        c.set(Calendar.MINUTE, p2)
        c.set(Calendar.SECOND, 0)
        startAlarm(c, "콜라",1)
    }

}