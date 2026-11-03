package com.example.lifeadmin.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lifeadmin.domain.model.Task

@Composable
fun TaskScreen(
    tasks: List<Task>,
    error: String?,
    onAddTask: (String, String) -> Unit,
    onCompleteTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit


) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "LifeAdmin",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onAddTask(title, description)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn {

            items(tasks, key = { it.id }) { task ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = {
                                onCompleteTask(task)
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            if (task.description.isNotEmpty()) {
                                Text(text = task.description)
                            }
                        }

                        TextButton(
                            onClick = {
                                onDeleteTask(task)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}