package com.example.taskmanager

import ToDoItem
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.annotation.SuppressLint

import androidx.activity.enableEdgeToEdge
import android.app.AlertDialog
import android.content.Context
import android.view.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent


import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView


import android.widget.EditText
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.taskmanager.TaskAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tasks: MutableList<ToDoItem>

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
        setContentView(R.layout.activity_main)


        tasks = getTasks().toMutableList()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(tasks,
            onEditClick = { task -> showEditDialog(task) },
            onDeleteClick = { task -> deleteTask(task) }
        )
        recyclerView.adapter = taskAdapter


        findViewById<View>(R.id.add_task_button).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val taskNameEditText = dialogView.findViewById<EditText>(R.id.task_name_edittext)
        val taskDescriptionEditText = dialogView.findViewById<EditText>(R.id.task_description_edittext)
        val taskDateEditText = dialogView.findViewById<EditText>(R.id.task_date_edittext)
        val taskTimeEditText = dialogView.findViewById<EditText>(R.id.task_time_edittext)

        AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val taskName = taskNameEditText.text.toString()
                val taskDescription = taskDescriptionEditText.text.toString()
                val taskDate = taskDateEditText.text.toString()
                val taskTime = taskTimeEditText.text.toString()
                if (taskName.isNotEmpty() && taskDescription.isNotEmpty() && taskDate.isNotEmpty() && taskTime.isNotEmpty()) {
                    addTask(taskName, taskDescription, taskDate, taskTime)
                } else {
                    Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun showEditDialog(task: ToDoItem) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val taskNameEditText = dialogView.findViewById<EditText>(R.id.task_name_edittext)
        val taskDescriptionEditText = dialogView.findViewById<EditText>(R.id.task_description_edittext)
        val taskDateEditText = dialogView.findViewById<EditText>(R.id.task_date_edittext)
        val taskTimeEditText = dialogView.findViewById<EditText>(R.id.task_time_edittext)

        taskNameEditText.setText(task.taskName)
        taskDescriptionEditText.setText(task.description)
        taskDateEditText.setText(task.date)
        taskTimeEditText.setText(task.time)

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                task.taskName = taskNameEditText.text.toString()
                task.description = taskDescriptionEditText.text.toString()
                task.date = taskDateEditText.text.toString()
                task.time = taskTimeEditText.text.toString()
                saveTasks()
                taskAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun addTask(taskName: String, description: String, date: String, time: String) {
        val task = ToDoItem(
            id = System.currentTimeMillis(),
            taskName = taskName,
            description = description,
            date = date,
            time = time
        )
        tasks.add(task)
        saveTasks()
        taskAdapter.notifyDataSetChanged()
    }

    private fun deleteTask(task: ToDoItem) {
        tasks.remove(task)
        saveTasks()
        taskAdapter.notifyDataSetChanged()
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("ToDoApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        editor.putString("tasks", gson.toJson(tasks))
        editor.apply()
    }

    private fun getTasks(): List<ToDoItem> {
        val sharedPreferences = getSharedPreferences("ToDoApp", Context.MODE_PRIVATE)
        val gson = Gson()
        val tasksJson = sharedPreferences.getString("tasks", null) ?: return emptyList()
        val type = object : TypeToken<List<ToDoItem>>() {}.type
        return gson.fromJson(tasksJson, type)
    }





}