package com.example.taskmanager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent

import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class timer : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button
    private lateinit var timeInput: EditText

    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private var timeLeftInMillis: Long = 0 // Default 0 minute


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_timer)


        var button10 = findViewById<Button>(R.id.button10)
        button10.setOnClickListener {
            val intent1 = Intent (this, menu::class.java)
            startActivity(intent1)
        }



        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)
        timeInput = findViewById(R.id.timeInput)

        loadTimerState()

        startButton.setOnClickListener { startTimer() }
        pauseButton.setOnClickListener { pauseTimer() }
        resetButton.setOnClickListener { resetTimer() }

        updateTimerText()
    }

    private fun startTimer() {
        if (isTimerRunning) return

        val inputTime = timeInput.text.toString().toLongOrNull()
        timeLeftInMillis = (inputTime ?: 1) * 60000 // Convert minutes to milliseconds

        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
                saveTimerState()
            }

            override fun onFinish() {
                isTimerRunning = false
                updateTimerText()
            }
        }.start()

        isTimerRunning = true
    }

    private fun pauseTimer() {
        if (!isTimerRunning) return

        timer?.cancel()
        isTimerRunning = false
        saveTimerState()
    }

    private fun resetTimer() {
        timeLeftInMillis = 0 // Reset to 0 minute
        updateTimerText()
        if (isTimerRunning) {
            pauseTimer()
        }
        saveTimerState()
    }

    private fun updateTimerText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = timeFormatted
    }

    private fun saveTimerState() {
        val sharedPreferences = getSharedPreferences("TimerApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("timeLeftInMillis", timeLeftInMillis)
        editor.putBoolean("isTimerRunning", isTimerRunning)
        editor.apply()
    }

    private fun loadTimerState() {
        val sharedPreferences = getSharedPreferences("TimerApp", Context.MODE_PRIVATE)
        timeLeftInMillis = sharedPreferences.getLong("timeLeftInMillis", 60000)
        isTimerRunning = sharedPreferences.getBoolean("isTimerRunning", false)

        if (isTimerRunning) {
            startTimer()
        }
    }
}