package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding) // Отступы от краев экрана телефона
                            .padding(16.dp),       // Внутренний отступ для красоты
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp) // Авто-отступы между блоками
                    ) {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Box(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                                App()
                            }
                        }

                    }
                }
            }
        }
    }
}


data class Task(val id: Int, val title: String, val done : Boolean = false)

@Composable
fun TaskList(
    onTaskClick: (Int) -> Unit,
    viewModel: TaskViewModel = viewModel()
){
    var text by rememberSaveable(


    ) { mutableStateOf("") }

    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = text,
                onValueChange = {text = it},
                label = { Text("Новая задача") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { viewModel.addTask(text)
                text = "" }
            ) { Text("Добавить") }
        }

        LazyColumn {
            itemsIndexed(viewModel.task)
            {
                index, task -> Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { viewModel.remove(index) }
                    ){
                        Text("Удалить")
                    }
                    Checkbox(
                       checked = task.done,
                       onCheckedChange = {viewModel.toggle(index) }
                   )
                Text(
                    text = task.title,
                    textDecoration = if (task.done) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTaskClick(task.id) }
                )

                }

            }
        }

    }
}

@Composable
fun TaskDetail(
    taskId: Int,
    onBack: () -> Unit,
    viewModel: TaskViewModel = viewModel()
){
    val task = viewModel.getTask(taskId)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (task != null)
        {
            Text(text = task.title, fontSize = 24.sp)
            Text(text = if(task.done) "Выполнена" else "Не выполнена")
        } else{
            Text("Задача не найдена")
        }
        Button(onClick = onBack){
            Text("Назад")
        }
    }
}


