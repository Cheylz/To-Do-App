package com.example.todoapp
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay


@Serializable
data object TaskListKey : NavKey

@Serializable
data class TaskDetailKey(val taskId: Int) : NavKey

@Composable
fun App()
{
    val backStack = rememberNavBackStack(TaskListKey)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider{
            entry<TaskListKey>{
                TaskList(onTaskClick = { id -> backStack.add(TaskDetailKey(id)) })
            }
            entry<TaskDetailKey>{
                key -> TaskDetail(
                    taskId = key.taskId,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
