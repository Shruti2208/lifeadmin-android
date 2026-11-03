package com.example.lifeadmin.data


import com.example.lifeadmin.domain.model.Task
import com.example.lifeadmin.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskRepository : TaskRepository {

    private val tasks = MutableStateFlow<List<Task>>(emptyList())
    private var nextId = 1
    override fun getTasks(): Flow<List<Task>> {
        return tasks
    }

    override suspend fun addTask(task: Task) {
        tasks.update { currentTasks ->

            val newTask = task.copy(
                id = nextId++
            )

            currentTasks + newTask
        }
    }

    override suspend fun deleteTask(task: Task) {
        tasks.update { currentTasks ->
            currentTasks.filter { it.id != task.id }//filter creates a new list containing only the elements for which the condition is true
            //We use != because we want to keep every task except the one we're deleting.
        }
    }

    override suspend fun updateTask(task: Task) {
        tasks.update { currentTasks ->
            currentTasks.map {
                if (it.id == task.id) task else it
            }
        }
    }
}