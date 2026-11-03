package com.example.lifeadmin.domain.usecase

import com.example.lifeadmin.domain.model.Task
import com.example.lifeadmin.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}