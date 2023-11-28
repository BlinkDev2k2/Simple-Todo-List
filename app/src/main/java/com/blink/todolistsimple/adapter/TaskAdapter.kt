package com.blink.todolistsimple.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.blink.todolistsimple.R
import com.blink.todolistsimple.model.Task

class TaskAdapter(private val context: Context, private val listTask: ArrayList<Task>, private val listener: SetTaskButtonListener) : BaseAdapter() {

    override fun getCount(): Int {
        return listTask.size
    }

    override fun getItem(position: Int): Any {
        return listTask[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val taskViewHolder: TaskViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)
            taskViewHolder = TaskViewHolder(view)
            view.tag = taskViewHolder
        } else  taskViewHolder = view.tag as TaskViewHolder
        taskViewHolder.name.text = listTask[position].name
        taskViewHolder.isDone.isChecked = listTask[position].isDone
        taskViewHolder.isDone.setOnCheckedChangeListener { buttonView, isChecked ->
            val byte = if (isChecked) 1.toByte() else 0.toByte()
            listener.onCheckedChanged(listTask[position].id, byte)
        }
        taskViewHolder.editTask.setOnClickListener {
            listener.onEditClicked(listTask[position].id)
        }
        taskViewHolder.delTask.setOnClickListener {
            listener.onDelClicked(listTask[position].id)
        }
        return view!!
    }

    companion object {
        private data class TaskViewHolder(private val view: View) {
            lateinit var name: TextView
            lateinit var isDone: CheckBox
            lateinit var editTask: ImageButton
            lateinit var delTask: ImageButton

            init {
                bind()
            }

            private fun bind() {
                name = view.findViewById(R.id.tvItemTodo)
                isDone = view.findViewById(R.id.cbDoneTask)
                editTask = view.findViewById(R.id.btnEditTodo)
                delTask = view.findViewById(R.id.btnDelTodo)
            }
        }
    }

    interface SetTaskButtonListener {
        fun onEditClicked(id: Short)
        fun onDelClicked(id: Short)
        fun onCheckedChanged(id: Short, state: Byte)
    }
}