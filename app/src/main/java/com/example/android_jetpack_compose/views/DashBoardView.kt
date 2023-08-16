package com.example.android_jetpack_compose.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_jetpack_compose.ui.theme.AndroidjetpackcomposeTheme

val primaryColor = Color(0xFF081f68);
val grayColor = Color(0xFFd9dae3);

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardView() {
    Scaffold(
        topBar = {
            AppBar(title = "Overview")
        },
        content = { contentPadding ->

            Column(
                Modifier
                    .padding(contentPadding)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                // week tracking
                WeekTracker()
                //month budget
            }
        }
    )
}

@Composable
fun WeekTracker() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        TrackingColum(percent = 1.0)
        TrackingColum(percent = 0.5)
        TrackingColum(percent = 0.7)
        TrackingColum(percent = 0.3)
        TrackingColum(percent = 0.2)
    }
}

@Composable
private fun TrackingColum(height: Double? = null, width: Double? = null, percent: Double = 0.0) {
    val trackColumnHeight: Double = 150.0
    val trackColumnWidth: Double = 40.0
    val finalHeight = height ?: trackColumnHeight
    val finalWidth = width ?: trackColumnWidth
    val borderRadius = 8.0;
    Box(
        modifier = Modifier
            .background(color = grayColor, shape = RoundedCornerShape(borderRadius.dp))
            .height(finalHeight.dp)
            .width(finalWidth.dp)
            .clip(RoundedCornerShape(borderRadius.dp))

    ) {

        Box(
            modifier = Modifier
                .background(color = primaryColor)
                .height((finalHeight * percent).dp)
                .width(finalWidth.dp)
                .align(alignment = Alignment.BottomCenter),
        )
    }
}

@Composable
fun SimpleColumn() {
    Column {
        Text(
            text = "Column Text 1",
            Modifier.background(Color.Red)
        )
        Text(text = "Column Text 2", Modifier.background(Color.White))
        Text(text = "Column Text 3", Modifier.background(Color.Green))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String) {

    // TopAppBar Composable
    TopAppBar(
        // Provide Title
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,

                )
        },

        )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidjetpackcomposeTheme {
        DashBoardView()
    }
}
