package com.example.i_go.feature_note.presentation.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.presentation.add_edit_patient.addFocusCleaner
import com.example.i_go.feature_note.presentation.login.components.CustomText
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.ui.theme.*
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun LoginScreen(
    navController: NavController
) {
    val idValue = rememberSaveable { mutableStateOf("") }
    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue =  rememberSaveable { mutableStateOf("") }
    val passwordConfirmValue = rememberSaveable { mutableStateOf("") }

    val logIdValue = rememberSaveable { mutableStateOf("") }
    val logPasswordValue = rememberSaveable { mutableStateOf("") }


    val focusManager = LocalFocusManager.current
    var scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    
    var isLogIn by remember { mutableStateOf(true) }
    var isMaxSize by remember { mutableStateOf(false) }
    val state = rememberCollapsingToolbarScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Box(
                modifier = Modifier
                    .background(call_color)
                    .fillMaxWidth()
                    .clickable { isMaxSize = !isMaxSize }
            ){
                Image(
                    painterResource(id = R.drawable.image_splash),
                    contentDescription = "image_splash",
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(bottom = if (isLogIn) 100.dp else 0.dp)
                        .size(50.dp)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(call_color),
            contentAlignment = BottomCenter
        ) {
            CollapsingToolbarScaffold(
                modifier = Modifier.fillMaxSize(),
                state = state,
                scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
                toolbarModifier = if (isMaxSize) Modifier.animateContentSize().size(0.dp) else Modifier.animateContentSize(),
                toolbar ={
                    Box(
                        modifier = Modifier.fillMaxWidth().pin()
                    )
                    MiddleImage(
                        Modifier
                            .padding(top = if (isLogIn) 0.dp else 100.dp)
                            .padding(bottom = 100.dp)
                            .fillMaxWidth()
                            .align(Center)
                            .parallax(0.5f)
                            .graphicsLayer {
                                // change alpha of Image as the toolbar expands
                                alpha = state.toolbarState.progress
                            }
                            .clickable { isMaxSize = !isMaxSize }
                    )
                }
            ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(card_color)
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
                    .addFocusCleaner(focusManager)
                    .animateContentSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.padding(if (isLogIn) 30.dp else 10.dp))

                    if (isLogIn){
                        Text(
                            text = "로그인",
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.align(CenterHorizontally).clickable { isMaxSize = !isMaxSize }
                        )
                    } else{
                        Text(
                            text = "회원가입",
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.align(CenterHorizontally).clickable { isMaxSize = !isMaxSize }
                        )

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .width(if (isLogIn) 70.dp else 90.dp)
                            .height(3.dp)
                            .background(call_color)
                    )

                }
                Spacer(modifier = Modifier.padding(40.dp))

                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    if (isLogIn) {
                        CustomText("아이디   ", logIdValue)
                        CustomText("패스워드", logPasswordValue, true)
                    } else {
                        CustomText("아이디   ", idValue)
                        CustomText("이메일   ", emailValue)
                        CustomText("패스워드", passwordValue, true)
                        CustomText("패스워드", passwordConfirmValue, true)
                    }
                    Spacer(modifier = Modifier.padding(30.dp))
                    Button(
                        onClick = {
                            // TODO: 서버로 의료진의 로그인 정보 보내기

                            scope.launch {
                                if (!isLogIn) {
                                    if (idValue.value.isEmpty()) {
                                        IdExcept(idValue.value, scaffoldState)
                                    }
                                    else if (emailValue.value.isEmpty()) {
                                        EmailExcept(emailValue.value, scaffoldState)
                                    }
                                    else if (passwordValue.value.isEmpty()) {
                                        PasswordExcept(passwordValue.value, scaffoldState)
                                    }
                                    else if (passwordConfirmValue.value.isEmpty()) {
                                        PasswordExcept(passwordConfirmValue.value, scaffoldState)
                                    }
                                    else if (passwordConfirmValue.value != passwordValue.value) {
                                        WrongPasswordExcept(
                                            passwordConfirmValue.value,
                                            passwordValue.value,
                                            scaffoldState
                                        )
                                    }
                                    else {
                                        isLogIn = !isLogIn
                                    }
                                } else{
                                    if (logIdValue.value.isEmpty()) {
                                        IdExcept(logIdValue.value, scaffoldState)
                                    }
                                    else if (logPasswordValue.value.isEmpty()) {
                                        PasswordExcept(logPasswordValue.value, scaffoldState)
                                    }
                                    else {
                                        navController.navigate(Screen.DoctorScreen.route)
                                    }
                                }

                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = call_color),
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .width(220.dp)
                            .height(50.dp)
                            .clip(shape = RoundedCornerShape(26.dp))
                    ) {
                        Text(
                            text = if (isLogIn) "로그인하기" else "회원가입하기",
                            color = Color.White,
                            style = MaterialTheme.typography.h4,
                            fontSize = 20.sp
                        )
                    }
                    if (isLogIn) Text(
                        text = "회원가입하기",
                        style = MaterialTheme.typography.body1,
                        color = White,
                        modifier=  Modifier
                            .align(CenterHorizontally)
                            .padding(30.dp)
                            .clickable{
                                isLogIn = !isLogIn
                            }
                    )
                    //     Spacer(modifier = Modifier.padding(40.dp))
                }
            }
        }
    }

    }
}
