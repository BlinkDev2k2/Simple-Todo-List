package com.blink.todolistsimple.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.blink.todolistsimple.R

class EditTaskActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val intent = Intent()
            intent.putExtra(MainActivity.EXTRA_EDIT_TASK, findViewById<EditText>(R.id.edtGetNameTaskUpdate).text.toString().trim())
            intent.putExtra(MainActivity.EXTRA_SEND_TASK_ID, getIntent().getShortExtra(MainActivity.EXTRA_RECEIVE_TASK_ID, -1))
            setResult(RESULT_OK, intent)
            finish()
        }
        findViewById<Button>(R.id.btnCancel2).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}