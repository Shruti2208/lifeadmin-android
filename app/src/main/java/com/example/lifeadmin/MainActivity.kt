package com.example.lifeadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.lifeadmin.data.InMemoryTaskRepository
import com.example.lifeadmin.domain.usecase.AddTaskUseCase
import com.example.lifeadmin.presentation.TaskViewModel
import com.example.lifeadmin.presentation.screen.TaskScreen
import com.example.lifeadmin.ui.theme.LifeAdminTheme
import com.example.lifeadmin.domain.usecase.CompleteTaskUseCase
import com.example.lifeadmin.domain.usecase.DeleteTaskUseCase
class MainActivity : ComponentActivity() {

    private val repository = InMemoryTaskRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val addTaskUseCase = AddTaskUseCase(repository)//We're passing the repository into the use case.
        val completeTaskUseCase = CompleteTaskUseCase(repository)
        val deleteTaskUseCase = DeleteTaskUseCase(repository)

        val factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                return TaskViewModel(
                    //We're passing both dependencies into our ViewModel.
                    //This is constructor injection.
                    repository = repository,
                    addTaskUseCase = addTaskUseCase,
                    completeTaskUseCase = completeTaskUseCase,
                    deleteTaskUseCase = deleteTaskUseCase

                ) as T
            }
        }

        setContent {

            LifeAdminTheme {

                val taskViewModel: TaskViewModel = viewModel(
                    factory = factory
                )

                val tasks by taskViewModel.tasks.collectAsState()
                val error by taskViewModel.error.collectAsState()

                TaskScreen(
                    tasks = tasks,
                    error = error,
                    onAddTask = { title, description ->
                        taskViewModel.addTask(title, description)
                    },
                    onCompleteTask = { task ->
                        taskViewModel.completeTask(task)
                    },
                    onDeleteTask = { task ->
                        taskViewModel.deleteTask(task)
                    },

                )
            }
        }
    }
}