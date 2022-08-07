package com.example.i_go.feature_note.presentation.add_edit_patient.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.i_go.R

@Composable
fun PatientMap (
    modifier: Modifier = Modifier,
    imageWidth: Int = 682,
    imageHeight: Int = 484,

    stationX1: Int = 29,
    stationX2: Int = 109,
    stationX3: Int = 644,
    stationX4: Int = 579,
    stationY1: Int = 369,
    stationY2: Int = 119,
    stationY3: Int = 28,
    stationY4: Int = 423,
    x: Int,
    y: Int
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenWidth * imageHeight / imageWidth)
    ) {
        Image(
            painterResource(id = R.drawable.map),
            contentDescription = "hospital"
        )
        // x의 범위: 0에서 682, y 범위: 0에서 484.5 사이
        MakeCircle(x = stationX1, y = stationY1)
        MakeCircle(x = stationX2, y = stationY2)
        MakeCircle(x = stationX3, y = stationY3)
        MakeCircle(x = stationX4, y = stationY4)

        PatientCircle(x= x, y = y)

    }
}
@Composable
fun MakeCircle(
    modifier: Modifier = Modifier,
    imageWidth: Int = 682,
    imageHeight: Int = 484,
    x: Int,
    y: Int
){
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = Color.Blue,
            center = Offset(x = canvasWidth * x / imageWidth, y = canvasHeight * y / imageHeight),
            radius = 18f
        )
    }
}

@Composable
fun PatientCircle(
    modifier: Modifier = Modifier,
    imageWidth: Int = 682,
    imageHeight: Int = 484,
    x: Int,
    y: Int
){
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = Color.Red,
            center = Offset(x = canvasWidth * x / imageWidth, y = canvasHeight * y / imageHeight),
            radius = 25f

        )
    }
}