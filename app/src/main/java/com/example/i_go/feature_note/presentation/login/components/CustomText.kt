package com.example.i_go.feature_note.presentation.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.i_go.ui.theme.recruit_city

@Composable
fun CustomText(
    text: String,
    value: MutableState<String>,
    isPassword: Boolean = false
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
            .padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 15.dp),
            color = recruit_city
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp)
                .padding(end = 15.dp)
                .padding(top = 10.dp)
                .padding(bottom = 20.dp)
        ) {
            LoginRectangular()
            if (isPassword){
                BasicTextField(
                    value = value.value,
                    onValueChange = {
                        value.value = it
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            else{
                BasicTextField(
                    value = value.value,
                    onValueChange = {
                        value.value = it
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopStart),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    textStyle = MaterialTheme.typography.h5
                )
            }


        }
    }
}