package com.example.lifeadmin.domain.usecase

import com.example.lifeadmin.domain.model.Task
import com.example.lifeadmin.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {

        val title = task.title.trim()

        require(title.isNotEmpty()) {
            "Task title cannot be empty"
        }

        repository.addTask(
            task.copy(title = title)
        )
    }
}