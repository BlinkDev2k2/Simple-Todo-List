package com.blink.todolistsimple.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.blink.todolistsimple.R
import com.blink.todolistsimple.model.Task
import com.blink.todolistsimple.adapter.TaskAdapter
import com.blink.todolistsimple.db.TodoDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var database: TodoDatabase? = null
    private lateinit var lisViewTask: ListView
    private lateinit var flbtnAddTask: FloatingActionButton
    private var adapter: TaskAdapter?= null
    private lateinit var data: ArrayList<Task>
    private val addNewTaskResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            database?.addNewTask(it.data?.getStringExtra(EXTRA_ADD_TASK) ?: "Add New Task Error")
            updateUI()
        } else {
            Toast.makeText(this, "Add New Task Error", Toast.LENGTH_SHORT).show()
        }
    }
    private val editTaskResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val value = it.data?.getShortExtra(EXTRA_SEND_TASK_ID, -1)
        if (it.resultCode == RESULT_OK && value != (-1).toShort()) {
            database?.editNameTask(value!!, it.data?.getStringExtra(EXTRA_EDIT_TASK) ?: "Edit task error")
            updateUI()
        } else {
            Toast.makeText(this, "Edit Task Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = TodoDatabase(applicationContext, "MyTask.sqlite", null, 1)
        lisViewTask = findViewById(R.id.listTask)
        flbtnAddTask = findViewById(R.id.flbtnAddTask)
    }

    private fun updateUI() {
        val cursor = database?.getAllData()
        if (cursor != null) {
            data.clear()
            while (cursor.moveToNext()) {
                val boolean = cursor.getShort(2) != 0.toShort()
                data.add(Task(cursor.getShort(0), cursor.getString(1), boolean))
            }
        }
        adapter?.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        val cursor = database?.getAllData()
        if (cursor != null) {
            data = ArrayList()
            while (cursor.moveToNext()) {
                val boolean = cursor.getShort(2) != 0.toShort()
                data.add(Task(cursor.getShort(0), cursor.getString(1), boolean))
            }
            adapter = TaskAdapter(this, data, object : TaskAdapter.SetTaskButtonListener {
                override fun onEditClicked(id: Short) {
                    val intent = Intent(this@MainActivity, EditTaskActivity::class.java)
                    intent.putExtra(EXTRA_RECEIVE_TASK_ID, id)
                    editTaskResultLauncher.launch(intent)
                }

                override fun onDelClicked(id: Short) {
                    database?.delTaskByName(id)
                    updateUI()
                }

                override fun onCheckedChanged(id: Short, state: Byte) {
                    database?.editStatementTask(id, state)
                }
            })
            lisViewTask.adapter = adapter
        }

        flbtnAddTask.setOnClickListener {
            addNewTaskResultLauncher.launch(Intent(this, AddTaskActivity::class.java))
        }
    }

    companion object {
        internal const val EXTRA_ADD_TASK = "com.blink.todolistsimple.EXTRA_ADD_TASK"
        internal const val EXTRA_EDIT_TASK = "com.blink.todolistsimple.EXTRA_EDIT_TASK"
        internal const val EXTRA_SEND_TASK_ID = "com.blink.todolistsimple.EXTRA_UPDATE_TASK_ID"
        internal const val EXTRA_RECEIVE_TASK_ID = "com.blink.todolistsimple.EXTRA_RECEIVE_TASK_ID"
    }
}