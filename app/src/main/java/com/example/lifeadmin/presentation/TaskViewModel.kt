package com.example.lifeadmin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifeadmin.domain.model.Task
import com.example.lifeadmin.domain.repository.TaskRepository
import com.example.lifeadmin.domain.usecase.AddTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.lifeadmin.domain.usecase.CompleteTaskUseCase
import com.example.lifeadmin.domain.usecase.DeleteTaskUseCase
class TaskViewModel(
    private val repository: TaskRepository,
    private val addTaskUseCase: AddTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val deleteTaskUseCase :DeleteTaskUseCase

) : ViewModel() {//Android's ViewModel is designed to hold UI-related state across configuration changes, such as screen rotation.

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())//_tasks is private and mutable. Only the ViewModel can update it.

    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()//tasks is public and read-only. The UI can observe it but cannot change its value directly.

    private val _error = MutableStateFlow<String?>(null)

    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            //viewModelScope.launch launches a coroutine tied to the ViewModel's lifecycle.

            //The coroutine collects updates from our repository's Flow.

            //Whenever the repository emits an updated task list, our ViewModel receives it.
            repository.getTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }
    fun addTask(title: String, description: String) {//Generating a simple ID by finding the highest existing task ID and adding 1.

        viewModelScope.launch {

            try {
                val newTask = Task(
                    id = 0,
                    title = title,
                    description = description
                )

                addTaskUseCase(newTask)

                _error.value = null

            } catch (e: IllegalArgumentException) {
                _error.value = e.message
            }
        }
    }
    fun completeTask(task: Task) {
        viewModelScope.launch {
            completeTaskUseCase(task)
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}