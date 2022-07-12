package com.example.i_go.feature_note.presentation

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.i_go.R
import com.example.i_go.feature_note.presentation.add_edit_patient.AddEditPatientScreen
import com.example.i_go.feature_note.presentation.patients.PatientsScreen
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.ui.theme.I_GOTheme
import com.example.i_go.ui.theme.call_color
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContent {
            I_GOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background

                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "splash_screen"
                    ) {
                        composable("splash_screen") {
                            SplashScreen(navController = navController)
                        }
                        composable(route = Screen.PatientsScreen.route) {
                            PatientsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditPatientScreen.route +
                                    "?patientId={patientId}&patientImage={patientImage}",
                            arguments = listOf(
                                navArgument(
                                    name = "patientId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "patientImage"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val image = it.arguments?.getInt("patientImage") ?: -1
                            AddEditPatientScreen(
                                navController = navController,
                                patientImage = image
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SplashScreen (navController: NavController) {
    val scale = remember {
        Animatable (0f)
    }
    LaunchedEffect(key1 = true) {

        scale.animateTo (
            targetValue = 0.3f,
            animationSpec = tween (
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
        delay(700L)
        navController.navigate(Screen.PatientsScreen.route)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
                           .background(call_color)
    ) {
        Image (
            painter = painterResource(id = R.drawable.image_splash),
            contentDescription = "LOGO",
            modifier = Modifier
                .scale(scale.value)
        )
        Text (
            text = " from\n기컴프",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        )
    }
}