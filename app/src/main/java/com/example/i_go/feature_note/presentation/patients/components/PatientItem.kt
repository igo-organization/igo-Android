package com.example.i_go.feature_note.presentation.patients.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.i_go.R
import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.ui.theme.button_color
import com.example.i_go.ui.theme.call_color
import com.example.i_go.ui.theme.card_color

@Composable
fun PatientItem(
    patient: Patient,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 15.dp,
    cutCornerSize: Dp = 0.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier
            .matchParentSize()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = card_color,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box( modifier = Modifier
                            .size(70.dp)
                            .shadow(5.dp, CircleShape)
                            .clip(CircleShape)
                            .background(White)
            ) {
                Image(
                    painterResource(id = Patient.patient_image_real[patient.image]),
                    contentDescription = "painting",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 2.dp)
                        .size(60.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ){
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = patient.name,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = patient.sex,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = " / ")
                    Text(
                        text = patient.age,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = " / ")
                    Text(
                        text = patient.blood_type,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = " / ")
                    Text(
                        text = patient.disease,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(30.dp)
                .padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = Color.White
            )
        }
        Button (
            onClick = {
                // TODO
            },
            colors =  ButtonDefaults
                .buttonColors(backgroundColor = call_color),
            modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
        ) {
           Text(
               text = "호출하기",
               color = White,
               style = MaterialTheme.typography.h4
           )
        }

    }
}