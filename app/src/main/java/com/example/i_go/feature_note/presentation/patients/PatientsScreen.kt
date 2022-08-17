package com.example.i_go.feature_note.presentation.patients

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.presentation.patients.components.OrderSection
import com.example.i_go.feature_note.presentation.patients.components.PatientItem
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.ui.theme.button_color
import com.example.i_go.ui.theme.dark_blue
import com.example.i_go.ui.theme.primary
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@ExperimentalAnimationApi
@Composable
fun PatientsScreen(
    navController: NavController,
    viewModel: PatientsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditPatientScreen.route)
                },
                backgroundColor = button_color
            ) {
                Icon(imageVector = Icons.Default.Add, tint = Color.White, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = button_color,
                    snackbarData = data
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "I-GO",
                    style = MaterialTheme.typography.h1,
                    fontWeight = FontWeight.Bold,
                    color = primary,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Row (
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.DoctorScreen.route)
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "profile",
                            tint = dark_blue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            //viewModel.onEvent(PatientsEvent.ToggleOrderSection)
                            //TODO: 언젠가는 만들 알림뷰
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bell),
                            contentDescription = "Alarm",
                            tint = dark_blue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onEvent(PatientsEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort",
                            tint = dark_blue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Divider(color = primary, modifier = Modifier.shadow(8.dp), thickness = 2.dp)


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item{
                    AnimatedVisibility(
                        visible = state.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        OrderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 11.dp)
                                .padding(bottom = 5.dp),
                            patientOrder = state.patientOrder,
                            onOrderChange = {
                                viewModel.onEvent(PatientsEvent.Order(it))
                            }
                        )
                    }
                }
                items(state.patients) { patient ->
                    PatientItem(
                        patient = patient,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                                navController.navigate(
                                    Screen.AddEditPatientScreen.route +
                                            "?patientId=${patient.id}&patientImage=${patient.image}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(PatientsEvent.DeletePatients(patient))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(

                                    message = "환자가 삭제되었습니다.",
                                    actionLabel = "취소하기"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(PatientsEvent.RestorePatients)
                                }
                            }
                        }
                    )
                }

            }
        }
    }
}