package com.example.i_go.feature_note.presentation.patients

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.data.storage.idStore
import com.example.i_go.feature_note.domain.repository.UserRepository
import com.example.i_go.feature_note.domain.util.log
import com.example.i_go.feature_note.presentation.patients.components.PatientItem
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.name_dataStore
import com.example.i_go.ui.theme.button_color
import com.example.i_go.ui.theme.dark_blue
import com.example.i_go.ui.theme.primary
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

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

    name.value.toString().log()

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
                                // navController.navigate(
                                //Screen.AddEditPatientScreen.route +
                                //    "?patientId=${patient.id}&patientImage=${patient.image}"
                                ///)
                            },
                            onDeleteClick = {
                                /*
                                viewModel.onEvent(PatientsEvent.DeletePatients(patient))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(

                                        message = "환자가 삭제되었습니다.",
                                        actionLabel = "취소하기"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(PatientsEvent.RestorePatients)
                                    }
                                }*/
                            }
                        )
                    }
                }


            }
        }
    }
}