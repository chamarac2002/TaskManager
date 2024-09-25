package com.example.taskmanager

import ToDoItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R


class TaskAdapter(
    private val tasks: MutableList<ToDoItem>,
    private val onEditClick: (ToDoItem) -> Unit,
    private val onDeleteClick: (ToDoItem) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.task_name)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_description)
        val taskDateTextView: TextView = itemView.findViewById(R.id.task_date)
        val taskTimeTextView: TextView = itemView.findViewById(R.id.task_time)
        val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskNameTextView.text = task.taskName
        holder.taskDescriptionTextView.text = task.description
        holder.taskDateTextView.text = task.date  // Add this line
        holder.taskTimeTextView.text = task.time  // Add this line

        holder.editButton.setOnClickListener {
            onEditClick(task)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(task)
        }
    }


    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<ToDoItem>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }
}

