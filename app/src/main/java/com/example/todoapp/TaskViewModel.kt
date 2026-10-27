package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
class TaskViewModel : ViewModel() {
    private val _task = mutableStateListOf<Task>()

    val task: List<Task> = _task

    private var nextId = 1

    fun addTask(title: String)
    {
        if(title.isNotBlank())
        {
            _task.add(Task(id = nextId++, title = title.trim()))
        }
    }

    fun getTask(id: Int): Task? = _task.find { it.id == id }

    fun toggle(index: Int)
    {
        _task[index] = _task[index].copy(done = !_task[index].done)
    }
    fun remove(index: Int)
    {
        _task.removeAt(index)
    }


}