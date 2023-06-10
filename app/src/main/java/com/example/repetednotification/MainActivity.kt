package com.example.repetednotification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.repetednotification.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        val clock = Calendar.getInstance()
        val currentHour = clock.get(Calendar.HOUR_OF_DAY)
        val currentMinute= clock.get(Calendar.MINUTE)
        mainBinding.setTime.setOnClickListener {
            val timepicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText("Set Time Notification")
                .build()
            timepicker.show(supportFragmentManager,"1")
              timepicker.addOnPositiveButtonClickListener {
                  clock.set(Calendar.HOUR_OF_DAY,timepicker.hour)
                  clock.set(Calendar.MINUTE,timepicker.minute)
                  clock.set(Calendar.SECOND,0)
                  clock.set(Calendar.MILLISECOND,0)
                  val intent = Intent(applicationContext,NotificationReciver::class.java)
                  val pendingIntent = if (Build.VERSION.SDK_INT>=23){
                       PendingIntent.getBroadcast(applicationContext,100,
                       intent,
                       PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                  }
                  else{
                      PendingIntent.getBroadcast(applicationContext,100,
                          intent,
                          PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                  }
                  val alarmManager:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                  alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP
                  ,clock.timeInMillis
                  ,AlarmManager.INTERVAL_DAY,
                  pendingIntent)
                  Toast.makeText(applicationContext,"The Alarm has been set!!",Toast.LENGTH_SHORT).show()
              }



        }
    }
}