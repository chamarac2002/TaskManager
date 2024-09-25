package com.example.taskmanager



import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var button7 = findViewById<Button>(R.id.button7)
        button7.setOnClickListener {
            val intent1 = Intent (this, MainActivity::class.java)
            startActivity(intent1)
        }

        var button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            val intent1 = Intent (this, RemindersActivity::class.java)
            startActivity(intent1)
        }

        var button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val intent1 = Intent (this, timer::class.java)
            startActivity(intent1)
        }

        var button13 = findViewById<Button>(R.id.button13)
        button13.setOnClickListener {
            val intent1 = Intent (this, stopwatch::class.java)
            startActivity(intent1)
        }

        var imageView4 = findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener {
            val intent1 = Intent (this, first::class.java)
            startActivity(intent1)
        }

        val dateTimeTextView: TextView = findViewById(R.id.dateTimeTextView)
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd / HH:mm", Locale.getDefault()).format(
            Date()
        )
        dateTimeTextView.text = currentDateTime



    }
}