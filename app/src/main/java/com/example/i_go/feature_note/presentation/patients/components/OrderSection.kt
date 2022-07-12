package com.example.i_go.feature_note.presentation.patients.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.i_go.feature_note.domain.util.OrderType
import com.example.i_go.feature_note.domain.util.PatientOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    patientOrder: PatientOrder = PatientOrder.Date(OrderType.Descending),
    onOrderChange: (PatientOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "이름",
                selected = patientOrder is PatientOrder.Name,
                onSelect = { onOrderChange(PatientOrder.Name(patientOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "날짜",
                selected = patientOrder is PatientOrder.Date,
                onSelect = { onOrderChange(PatientOrder.Date(patientOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "질병",
                selected = patientOrder is PatientOrder.Diseases,
                onSelect = { onOrderChange(PatientOrder.Diseases(patientOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "이미지",
                selected = patientOrder is PatientOrder.Image,
                onSelect = { onOrderChange(PatientOrder.Image(patientOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "오름차순",
                selected = patientOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(patientOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "내림차순",
                selected = patientOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(patientOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}