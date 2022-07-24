package com.example.i_go.feature_note.presentation.login

import androidx.compose.material.ScaffoldState

suspend fun IdExcept(
    idValue: String,
    scaffoldState: ScaffoldState
) {
    if (idValue.isEmpty()){
        scaffoldState.snackbarHostState.showSnackbar(
            message = "아이디를 입력해주세요."
        )
    }
}

suspend fun EmailExcept(
    EmailValue: String,
    scaffoldState: ScaffoldState
) {
    if (EmailValue.isEmpty()){
        scaffoldState.snackbarHostState.showSnackbar(
            message = "이메일을 입력해주세요."
        )
    }
}

suspend fun PasswordExcept(
    PasswordValue: String,
    scaffoldState: ScaffoldState
) {
    if (PasswordValue.isEmpty()){
        scaffoldState.snackbarHostState.showSnackbar(
            message = "패스워드를 입력해주세요."
        )
    }
}

suspend fun WrongPasswordExcept(
    PasswordValue: String,
    PasswordConfirmValue: String,
    scaffoldState: ScaffoldState
) {
    if (PasswordValue != PasswordConfirmValue){
        scaffoldState.snackbarHostState.showSnackbar(
            message = "패스워드가 틀렸습니다."
        )
    }
}