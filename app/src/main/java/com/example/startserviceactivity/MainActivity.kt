package com.example.startserviceactivity

import android.adservices.measurement.WebSourceRegistrationRequest
import android.app.Service
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val EXTRA_SECONDS = "extra_seconds"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            val timer = findViewById<EditText>(R.id.timerEditText).text.toString()
            val intent = Intent(this, TimerService::class.java)
            intent.putExtra(EXTRA_SECONDS, timer.toInt())
            startService(intent)
        }
    }
}

class TimerService : Service() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val passingData = intent?.getIntExtra(EXTRA_SECONDS, 10)

        if (passingData != null) {
            runTimer(passingData)
        }

        return super.onStartCommand(intent, flags, startId)
    }


    fun runTimer(time : Int) {
        scope.launch {
            repeat(time) {
                Log.d("Countdown", (time-it).toString())
                delay(1000)
            }
        }
    }

}