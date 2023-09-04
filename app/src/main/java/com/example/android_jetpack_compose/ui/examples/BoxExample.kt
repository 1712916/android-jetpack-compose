package com.example.android_jetpack_compose.ui.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BoxExample() {

    BoxWithConstraints {
        val boxWithConstraintsScope = this
        val yOffset = 0.2 * boxWithConstraintsScope.maxHeight.value

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
                    .background(Color.Red)

            )
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
//                    .offset(y =  yOffset.dp)
                    .absoluteOffset(10.dp, 20.dp)
                    .height(100.dp)
                    .width(100.dp)
                    .background(Color.Blue)
            )
            Text(
                "Layout offset modifier sample",
                Modifier.fillMaxSize()
//                    .wrapContentSize(Alignment.Center)
                    .absoluteOffset(10.dp, 20.dp)
            )
        }
    }
}
