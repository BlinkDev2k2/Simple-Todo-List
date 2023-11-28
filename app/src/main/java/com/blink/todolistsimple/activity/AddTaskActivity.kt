package com.blink.todolistsimple.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.blink.todolistsimple.R

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val intent = Intent()
            intent.putExtra(MainActivity.EXTRA_ADD_TASK, findViewById<EditText>(R.id.edtAddNameTask).text.toString().trim())
            setResult(RESULT_OK, intent)
            finish()
        }
        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}