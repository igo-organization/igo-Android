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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.add_edit_patient.addFocusCleaner
import com.example.i_go.feature_note.presentation.login.components.CustomText
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()

) {
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginViewModel.UiEvent.Error -> {
                    "LOGIN ERROR!!".log()
                }
                is LoginViewModel.UiEvent.Login -> {
                    "LOGIN SUCCESS!!".log()
                    navController.navigate(Screen.DoctorScreen.route)
                }
            }
        }
    }
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
            ) {
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
                toolbarModifier = if (isMaxSize) Modifier.animateContentSize()
                    .size(0.dp) else Modifier.animateContentSize(),
                toolbar = {
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

                        if (isLogIn) {
                            Text(
                                text = "로그인",
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier.align(CenterHorizontally)
                                    .clickable { isMaxSize = !isMaxSize }
                            )
                        } else {
                            Text(
                                text = "회원가입",
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier.align(CenterHorizontally)
                                    .clickable { isMaxSize = !isMaxSize }
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
                            CustomText(
                                text = "아이디   ",
                                viewModel.emailPw.value.username,
                                onValueChange = { viewModel.onEvent(LoginEvent.EnteredName(it)) })
                            CustomText(
                                text = "패스워드",
                                viewModel.emailPw.value.password,
                                onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                                true
                            )
                        } else {
                            CustomText("아이디   ")
                            CustomText("이메일   ")
                            CustomText("패스워드")
                            CustomText("패스워드")
                        }
                        Spacer(modifier = Modifier.padding(30.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    if (!isLogIn) {
                                        //    IdExcept(idValue.value, scaffoldState)
                                        //     EmailExcept(emailValue.value, scaffoldState)
                                        //     PasswordExcept(passwordValue.value, scaffoldState)
                                        //     PasswordExcept(passwordConfirmValue.value, scaffoldState)
                                        //    WrongPasswordExcept(
                                        //         passwordConfirmValue.value,
                                        //        passwordValue.value,
                                        //         scaffoldState
                                        //     )
                                    } else {
                                        IdExcept(viewModel.emailPw.value.username, scaffoldState)
                                        PasswordExcept(
                                            viewModel.emailPw.value.password,
                                            scaffoldState
                                        )
                                        viewModel.onEvent(LoginEvent.Login)
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
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(30.dp)
                                .clickable {
                                    isLogIn = !isLogIn
                                }
                        )
                    }
                }
            }
        }
    }
}
