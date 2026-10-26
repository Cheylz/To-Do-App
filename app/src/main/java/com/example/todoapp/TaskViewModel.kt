package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
class TaskViewModel : ViewModel() {
    private val _task = mutableStateListOf<Task>()

    val task: List<Task> = _task

    fun addTask(title: String)
    {
        if(title.isNotBlank())
        {
            _task.add(Task(title.trim()))
        }
    }
    fun toggle(index: Int)
    {
        _task[index] = _task[index].copy(done = !_task[index].done)
    }
    fun remove(index: Int)
    {
        _task.removeAt(index)
    }


}