package com.example.i_go.feature_note.presentation.alarms.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.i_go.feature_note.data.remote.responseDTO.PatientByIdDTO
import com.example.i_go.ui.theme.call_color
import com.example.i_go.ui.theme.card_color
import com.example.i_go.ui.theme.text_gray

@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    image: Int = 1,
    name: String = "맹구",
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable(onClick = onClick)
            .background(card_color),
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxSize()
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(60.dp).align(CenterVertically),
            ) {
                Image(
                    painterResource(
                        id = PatientByIdDTO.patient_image_real[image]
                    ),
                    contentDescription = "painting"
                )
            }
            Column(
                modifier = Modifier.padding(start = 20.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "환자 호출",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(11.dp))
                Text(
                    text = name + " 환자의 호출",
                    style = MaterialTheme.typography.h5,
                    color = text_gray
                )
            }
        }
    }
}

