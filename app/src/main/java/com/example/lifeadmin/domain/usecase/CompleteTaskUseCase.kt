package com.example.lifeadmin.domain.usecase

import com.example.lifeadmin.domain.model.Task
import com.example.lifeadmin.domain.repository.TaskRepository

class CompleteTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {

        val updatedTask = task.copy(
            isCompleted = !task.isCompleted
        )

        repository.updateTask(updatedTask)
    }
}