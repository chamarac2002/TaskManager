package com.example.taskmanager



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Context
import android.content.Intent

import android.os.Handler
import android.widget.Button
import android.widget.TextView

import android.content.SharedPreferences

class stopwatch : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var startTime = 0L
    private var elapsedTime = 0L
    private var isRunning = false
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stopwatch)


        var button5 = findViewById<Button>(R.id.button5)
        button5.setOnClickListener {
            val intent1 = Intent (this, menu::class.java)
            startActivity(intent1)
        }


        // Initialize views
        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

        sharedPreferences = getSharedPreferences("StopwatchPrefs", Context.MODE_PRIVATE)
        handler = Handler()

        // Restore state from SharedPreferences
        startTime = sharedPreferences.getLong("startTime", 0L)
        elapsedTime = sharedPreferences.getLong("elapsedTime", 0L)
        isRunning = sharedPreferences.getBoolean("isRunning", false)

        if (isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            startTimer()
        } else {
            updateTimer(elapsedTime)
        }

        // Start Button
        btnStart.setOnClickListener {
            if (!isRunning) {
                startTime = System.currentTimeMillis()
                startTimer()
            }
        }

        // Pause Button
        btnPause.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            }
        }

        // Reset Button
        btnReset.setOnClickListener {
            resetTimer()
        }

    }

    private fun startTimer() {
        isRunning = true
        runnable = object : Runnable {
            override fun run() {
                elapsedTime = System.currentTimeMillis() - startTime
                updateTimer(elapsedTime)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun pauseTimer() {
        isRunning = false
        handler.removeCallbacks(runnable)
    }

    private fun resetTimer() {
        handler.removeCallbacks(runnable)
        isRunning = false
        elapsedTime = 0L
        startTime = 0L
        updateTimer(0L)
    }

    private fun updateTimer(timeInMillis: Long) {
        val seconds = (timeInMillis / 1000) % 60
        val minutes = (timeInMillis / (1000 * 60)) % 60
        val hours = (timeInMillis / (1000 * 60 * 60)) % 24
        tvTimer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onPause() {
        super.onPause()

        // Save the state of the stopwatch in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putLong("startTime", startTime)
        editor.putLong("elapsedTime", elapsedTime)
        editor.putBoolean("isRunning", isRunning)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()

        // Restore the state from SharedPreferences
        startTime = sharedPreferences.getLong("startTime", 0L)
        elapsedTime = sharedPreferences.getLong("elapsedTime", 0L)
        isRunning = sharedPreferences.getBoolean("isRunning", false)

        if (isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            startTimer()
        } else {
            updateTimer(elapsedTime)
        }
    }
}