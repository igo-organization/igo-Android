package com.example.i_go.feature_note.presentation.doctors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import com.example.i_go.R
import com.example.i_go.facility_dataStore
import com.example.i_go.feature_note.presentation.add_edit_patient.addFocusCleaner
import com.example.i_go.feature_note.presentation.util.Screen
import com.example.i_go.major_dataStore
import com.example.i_go.name_dataStore
import com.example.i_go.ui.theme.button_color
import com.example.i_go.ui.theme.card_color
import com.example.i_go.ui.theme.primary
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun DoctorScreen (
    navController : NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current

    var nameValue = rememberSaveable {
        mutableStateOf("")
    }
    var majorValue = rememberSaveable {
        mutableStateOf("")
    }
    var facilityValue = rememberSaveable {
        mutableStateOf("")
    }
    var context = LocalContext.current
    var scope = rememberCoroutineScope()

    val nameKey = stringPreferencesKey("doctor_name")
    var name = flow<String> {

        context.name_dataStore.data.map {
            it[nameKey]
        }.collect {
            if (it != null) {
                this.emit(it)
            }
        }
    }.collectAsState(initial = "")

    val majorKey = stringPreferencesKey("doctor_major")
    var major = flow<String> {

        context.major_dataStore.data.map {
            it[majorKey]
        }.collect {
            if (it != null) {
                this.emit(it)
            }
        }
    }.collectAsState(initial = "")

    val facilityKey = stringPreferencesKey("doctor_facility")
    var facility = flow<String> {

        context.facility_dataStore.data.map {
            it[facilityKey]
        }.collect {
            if (it != null) {
                this.emit(it)
            }
        }
    }.collectAsState(initial = "")


    LaunchedEffect(Unit) {
        nameValue.value = name.value
        majorValue.value = major.value
        facilityValue.value = facility.value
    }
    Scaffold(
            floatingActionButton = {
                if (nameValue.value.trim().isNotEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                saveDoctorName(
                                    context, nameValue.value
                                )
                                saveDoctorMajor(
                                    context, majorValue.value
                                )
                                saveDoctorFacility(
                                    context, facilityValue.value
                                )
                            }
                            navController.navigate(Screen.PatientsScreen.route)
                        },
                        backgroundColor = button_color
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            tint = Color.White,
                            contentDescription = "Save doctor"
                        )
                      }
                }
            }, scaffoldState = scaffoldState

            ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .addFocusCleaner(focusManager)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = "의료진 정보",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = primary,
                    textAlign = TextAlign.Center
                )
            }
            Divider(color = primary, modifier = Modifier.shadow(8.dp), thickness = 2.dp)
            Profile()
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment =  Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "이름",
                    modifier = Modifier
                        .padding(start = 30.dp)
                )
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()

                    BasicTextField(
                        value = nameValue.value,
                        onValueChange = {
                            nameValue.value = it
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(start = 10.dp),
                        textStyle = MaterialTheme.typography.body1,
                        singleLine = true,
                    )

                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment =  Alignment.CenterVertically
            ) {
                Text(
                    text = "전공",
                    modifier = Modifier
                        .padding(start = 30.dp)
                )
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()
                    BasicTextField(
                        value = majorValue.value,
                        onValueChange = { majorValue.value = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(start = 10.dp),
                        textStyle = MaterialTheme.typography.body1,
                        singleLine = true,
                    )
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment =  Alignment.CenterVertically
            ) {
                Text(
                    text = "시설",
                    modifier = Modifier
                        .padding(start = 30.dp)
                )
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 10.dp)
                ) {
                    MakeRectangular()
                    BasicTextField(
                        value = facilityValue.value,
                        onValueChange = { facilityValue.value = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(start = 10.dp),
                        textStyle = MaterialTheme.typography.body1,
                        singleLine = true,
                    )
                }
            }
        }
    }
}

@Composable
fun Profile() {
    Column(
        modifier = Modifier
            .padding(top = 60.dp, bottom = 60.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp)
        ) {
            Image(
                painterResource(id = R.drawable.diagnosis),
                contentDescription = "painting"
            )
        }
    }
}
@Composable
fun MakeRectangular() {
    val cornerRadius: Dp = 10.dp
    val cutCornerSize: Dp = 0.dp
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
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
                color = card_color,
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
}
