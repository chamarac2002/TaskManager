package com.example.taskmanager

import android.content.Intent
import android.widget.Button

data class Reminder(
    val id: Long = System.currentTimeMillis(),
    var title: String,
    var description: String = "",
    var dateTime: Long
)