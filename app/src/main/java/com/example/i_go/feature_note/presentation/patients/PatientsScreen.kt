package com.example.i_go.feature_note.presentation.patients

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.data.storage.idStore
import com.example.i_go.feature_note.domain.repository.UserRepository
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.patients.components.PatientItem
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.ui.theme.button_color
import com.example.i_go.ui.theme.call_color
import com.example.i_go.ui.theme.dark_blue
import com.example.i_go.ui.theme.primary
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
    var context = LocalContext.current

    val nameKey = stringPreferencesKey("user")
    var name = flow<String> {

        context.idStore.data.map {
            it[nameKey]
        }.collect {
            if (it != null) {
                this.emit(it)
            }
        }
    }.collectAsState(initial = "")

    val openDialog = remember { mutableStateOf(false) }
    var patientId by remember { mutableStateOf(1) }

    LaunchedEffect(key1 = true){
        viewModel.getPatients(if (name.value.isEmpty()) 1 else name.value.toInt())
    }
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
                    horizontalArrangement = Arrangement.End
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
                }
            }
            Divider(color = primary, modifier = Modifier.shadow(8.dp), thickness = 2.dp)
            if (openDialog.value){
                AlertDialog(
                    onDismissRequest = { openDialog.value = false},
                    title = {
                        Text(
                            text = "환자에게 보낼 메시지를 입력해주세요.",
                            style = MaterialTheme.typography.body1,
                            fontSize = 20.sp,
                            color = primary
                        )
                    },
                    text = {
                        Column{
                            Spacer(modifier = Modifier.height(20.dp))
                            BasicTextField(
                                value = viewModel.message.value.message!!,
                                onValueChange = {
                                    viewModel.onEvent(PatientsEvent.EnteredText(it), name.value.toInt(), patientId)
                                },
                                modifier = Modifier
                                        .padding(10.dp)
                                        .align(CenterHorizontally),
                                textStyle = MaterialTheme.typography.body1,
                                singleLine = true,
                            )
                            Divider(
                                modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth().height(1.dp),
                                color = call_color
                            )
                        }
                   },
                    confirmButton = {
                        Box {
                            Button(
                                modifier = Modifier.align(Center),
                                onClick = {
                                    viewModel.onEvent(
                                        PatientsEvent.CallPatient,
                                        doctor_id = name.value.toInt(),
                                        patient_id = patientId,
                                    )
                                    openDialog.value = false
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = call_color)
                            ) {
                                Text("호출하기", style = MaterialTheme.typography.h4, color = White)
                            }
                        }
                    },
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                viewModel.state.value.patientDTOS.forEach {
                    item {
                        PatientItem(
                            name = it.name!!,
                            gender = it.gender!!,
                            blood_type = it.blood_type!!,
                            disease = it.disease!!,
                            image = it.image!!,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                 navController.navigate(
                                Screen.AddEditPatientScreen.route +
                                    "?patientId=${it.id}&doctorId=${name.value.toInt()}"
                                 )
                            },
                            onDeleteClick = {
                                viewModel.onEvent(
                                    PatientsEvent.DeletePatient,
                                    doctor_id = name.value.toInt(),
                                    patient_id = it.id!!
                                )
                                scope.launch{
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "환자가 삭제되었습니다.",
                                        actionLabel = "취소하기"
                                    )
                                    viewModel.getPatients(if (name.value.isEmpty()) 1 else name.value.toInt())
                                }
                            },
                            onCallClick = {
                                openDialog.value = true
                                patientId = it.id!!
                            }
                        )
                    }
                }


            }
        }
    }
}