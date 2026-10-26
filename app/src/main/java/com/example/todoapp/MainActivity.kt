package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                        TaskList()

                    }
                }
            }
        }
    }
}


data class Task(val title: String, val done : Boolean = false)

@Composable
fun TaskList(
    viewModel: TaskViewModel = viewModel()
){
    var text by remember { mutableStateOf("") }

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

                    Text(text = task.title)
                }
            }
        }
    }


}

