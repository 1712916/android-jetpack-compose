package com.example.android_jetpack_compose.ui.daily_expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDailyExpenseView() {
    var text by remember { mutableStateOf("Hello") }

    Scaffold { p ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(p),
//            verticalArrangement =    Arrangement.SpaceBetween
        ){

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                content = {
                    item {
                        TextField(value = text, onValueChange = { text = it })
                    }
                    item {
                        OutlinedTextField(
                            value = text, onValueChange = { text = it },
                            label = { Text("Note") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {

                        Column {
                            Text("Categories")
                        }
                    }
                    item {
                        Column {
                            Text("Methods")
                        }
                    }
                    item {
                        Button(onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save")
                        }
                    }
                },
            )
            AppNumberBoard(onChanged = {  text = it }, text = text)
        }
    }
}
