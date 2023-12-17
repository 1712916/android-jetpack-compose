package com.example.android_jetpack_compose.ui.view.chart

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Blue = Color(0xFF007BFF)
@Composable
fun PieChart(
    data: Map<String, Long>,
    radiusOuter: Dp = 80.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    // add the colors as per the number of data(no. of pie chart entries)
    // so that each data will get a color
    val colors = data.keys.map {
        generateColor(it)
    }.toList()

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SizedBox(height = 20.0)

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
            DetailsPieChart(
                data = data,
                colors = colors
            )
        }

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsPieChart(
    data: Map<String, Long>,
    colors: List<Color>
) {

    CustomGridView (
        cols = 2,
        count = data.count(),
        ) {
        index ->
        DetailsPieChartItem(
            data = Pair(data.keys.elementAt(index),  data.values.elementAt(index)),
            color = colors[index]
        )
    }

}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Long>,
    height: Dp = 20.dp,
    color: Color,
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp),
        color = Color.Transparent
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column() {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }

    }

}

@Preview
@Composable
fun PieChartPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // Preview with sample data
        PieChart(
            data = mapOf(
                Pair("Ăn sáng", 150),
                Pair("Ăn trưa", 120),
                Pair("Ăn tối", 110),
                Pair("Cà phê", 170),
                Pair("Du lịch", 120),
            )
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomGridView(
    cols: Int,
    count: Int,
    builder: @Composable (Int) -> Unit
) {
    FlowRow(
        maxItemsInEachRow = cols,
    ) {
        repeat(count) {
            Box(Modifier.fillMaxWidth((1.0 / cols).toFloat())) {
                builder(it)
            }
        }
    }
}
