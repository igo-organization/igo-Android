package com.example.i_go.feature_note.presentation.login

import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Scroller
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.presentation.add_edit_patient.addFocusCleaner
import com.example.i_go.feature_note.presentation.doctors.*
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.PasswordAuthentication

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavController
) {
    val idValue = rememberSaveable { mutableStateOf("") }
    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue =  rememberSaveable { mutableStateOf("") }
    val passwordConfirmValue = rememberSaveable { mutableStateOf("") }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    var scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val sizeState by remember { mutableStateOf(200.dp) }
    var isTouched by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(call_color),
        contentAlignment = BottomCenter
    ) {
        Image(
            painterResource(id = R.drawable.image_splash),
            contentDescription = "image_splash",
            modifier = Modifier
                .clickable { isTouched = !isTouched }
                .align(TopStart)
                .padding(10.dp)
                .size(50.dp)
        )
        MiddleImage(
            Modifier
                .clickable { isTouched = !isTouched }
                .padding(top = 150.dp)
                .padding(bottom = 30.dp)
                .align(TopCenter)
        )
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(
                    if (isTouched) 0.9f else 0.2f
                )
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(card_color)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
                .addFocusCleaner(focusManager)
                .animateContentSize()
        ) {
            Column(
                modifier = Modifier.clickable{isTouched = !isTouched}
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "로그인",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .clickable { isTouched = !isTouched }
                        .align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider(modifier = Modifier.align(CenterHorizontally).width(70.dp).height(3.dp).background(call_color))

            }
            Spacer(modifier = Modifier.padding(40.dp))

            Column(
                horizontalAlignment = CenterHorizontally
            ) {

                CustomText("아이디   ", idValue)
                CustomText("이메일   ", emailValue)
                CustomText("패스워드", passwordValue, true)
                CustomText("패스워드", passwordConfirmValue, true)

                Spacer(modifier = Modifier
                    .padding(50.dp)
                    .clickable { isTouched = !isTouched })
                Button(
                    onClick = {
                        // TODO: 서버로 의료진의 로그인 정보 보내기

                        scope.launch{
                            if (idValue.value.isEmpty()) {
                                IdExcept(emailValue.value, scaffoldState)
                            }
                            if (emailValue.value.isEmpty()) {
                                EmailExcept(emailValue.value, scaffoldState)
                            }
                            if (passwordValue.value.isEmpty()) {
                                PasswordExcept(passwordValue.value, scaffoldState)
                            }
                            if (passwordConfirmValue.value.isEmpty()) {
                                PasswordExcept(passwordConfirmValue.value, scaffoldState)
                            }
                            if (passwordConfirmValue.value != passwordValue.value) {
                                WrongPasswordExcept(
                                    passwordConfirmValue.value,
                                    passwordValue.value,
                                    scaffoldState
                                )
                            }
                            if (idValue.value.isNotEmpty()
                                && emailValue.value.isNotEmpty()
                                && passwordValue.value.isNotEmpty()
                                && passwordConfirmValue.value.isNotEmpty()
                                && passwordConfirmValue.value == passwordValue.value
                            ) {
                                navController.navigate(Screen.DoctorScreen.route)
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
                        text = "로그인하기",
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(40.dp))
            }
        }
    }

    }
}

@Composable
fun MiddleImage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ){
        Column(
            modifier = Modifier
                .align(Center)
        ){
            Image(
                painterResource(id = R.drawable.hospital),
                contentDescription = "hospital",
                modifier = Modifier.align(CenterHorizontally).padding(bottom = 20.dp)
            )
            Row{
            Text(
                text = "의료진, 환자간\n위치 파악 시스템",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(15.dp)
                    .align(CenterVertically),
                color = White,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "아이고",
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .align(CenterVertically),
                color = button_color,
                textAlign = TextAlign.Center,
            )
            }


        }
    }
}
@Composable
fun LoginRectangular() {
    val cornerRadius: Dp = 10.dp
    val cutCornerSize: Dp = 0.dp
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
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
                color = White,
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
}
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
                        .align(TopStart),
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
                        .align(TopStart),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5
                )
            }


        }
    }
}