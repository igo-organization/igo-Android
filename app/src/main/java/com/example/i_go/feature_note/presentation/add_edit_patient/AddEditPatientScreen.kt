package com.example.i_go.feature_note.presentation.add_edit_patient

import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.i_go.R
import com.example.i_go.feature_note.domain.model.Patient
import com.example.i_go.feature_note.domain.model.Patient.Companion.patientImages
import com.example.i_go.feature_note.domain.model.Patient.Companion.patient_image_real
import com.example.i_go.feature_note.presentation.add_edit_patient.components.TransparentHintTextField
import com.example.i_go.feature_note.presentation.doctors.MakeRectangular
import com.example.i_go.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddEditPatientScreen(
    navController: NavController,
    patientImage: Int,
    viewModel: AddEditPatientViewModel = hiltViewModel()
) {
    val nameState = viewModel.patientName.value
    val sexState = viewModel.patientSex.value
    val ageState = viewModel.patientAge.value
    val bloodTypeState = viewModel.patientBloodType.value
    val diseasesState = viewModel.patientDiseases.value
    val extraState = viewModel.patientExtra.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val selectedGender = remember { mutableStateOf("")}
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current

    val pagerState = rememberPagerState(pageCount = 5, initialPage = if (patientImage > 0) patientImage else patientImages.random())


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditPatientViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditPatientViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditPatientEvent.ChangeImage(pagerState.currentPage))
                    viewModel.onEvent(AddEditPatientEvent.SavePatient)
                },
                backgroundColor = button_color
            ) {
                Icon(imageVector = Icons.Default.Save, tint = Color.White, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    text = "환자 정보",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = primary,
                    textAlign = TextAlign.Center
                )
            }
            Divider(color = primary, modifier = Modifier.shadow(8.dp), thickness = 2.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {


                HorizontalPager(
                    state = pagerState,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    Column(
                        modifier = Modifier
                            .padding(top = 30.dp, bottom = 8.dp)
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
                                painterResource(
                                    id = patient_image_real[page]
                                ),
                                contentDescription = "painting"
                            )
                        }
                    }
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(30.dp),
                    activeColor = card_color
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "이름",
                    modifier = Modifier
                        .padding(start = 30.dp),
                    color = recruit_city
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()
                    TransparentHintTextField(
                        text = nameState.text,
                        hint = nameState.hint,
                        modifier = Modifier
                            .padding(8.dp)
                            .focusRequester(focusRequester = focusRequester),

                        onValueChange = {
                            viewModel.onEvent(AddEditPatientEvent.EnteredName(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditPatientEvent.ChangeNameFocus(it))
                        },
                        isHintVisible = nameState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "성별",
                    modifier = Modifier
                        .padding(start = 30.dp),
                    color = recruit_city
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()/*
                    Row{

                        RadioButton(
                            selected = selectedGender.value == "남",
                            onClick = {
                                selectedGender.value == "남"
                                viewModel.onEvent(AddEditPatientEvent.EnteredSex(selectedGender.value))
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = call_color,
                                unselectedColor = call_color
                            )
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("남")
                        Spacer(modifier = Modifier.size(8.dp))

                        RadioButton(
                            selected = selectedGender.value == "여",
                            onClick = {
                                selectedGender.value == "여"
                                viewModel.onEvent(AddEditPatientEvent.EnteredSex(selectedGender.value))
                              },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = call_color,
                                unselectedColor = call_color
                            )
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("여")
                        Spacer(modifier = Modifier.size(8.dp))
                    }*/

                    TransparentHintTextField(
                        text = sexState.text,
                        hint = sexState.hint,
                        modifier = Modifier
                            .padding(8.dp)
                            .focusRequester(focusRequester = focusRequester),

                        onValueChange = {
                            viewModel.onEvent(AddEditPatientEvent.EnteredSex(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditPatientEvent.ChangeSexFocus(it))
                        },
                        isHintVisible = sexState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "나이",
                    modifier = Modifier
                        .padding(start = 30.dp),
                    color = recruit_city
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()
                    TransparentHintTextField(
                        text = ageState.text,
                        hint = ageState.hint,
                        modifier = Modifier
                            .padding(8.dp)
                            .focusRequester(focusRequester = focusRequester),

                        onValueChange = {
                            viewModel.onEvent(AddEditPatientEvent.EnteredAge(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditPatientEvent.ChangeAgeFocus(it))
                        },
                        isHintVisible = ageState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "혈액형",
                    modifier = Modifier
                        .padding(start = 25.dp),
                    color = recruit_city
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()
                    TransparentHintTextField(
                        text = bloodTypeState.text,
                        hint = bloodTypeState.hint,
                        modifier = Modifier
                            .padding(8.dp)
                            .focusRequester(focusRequester = focusRequester),

                        onValueChange = {
                            viewModel.onEvent(AddEditPatientEvent.EnteredBloodType(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditPatientEvent.ChangeBloodTypeFocus(it))
                        },
                        isHintVisible = bloodTypeState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "질병",
                    modifier = Modifier
                        .padding(start = 30.dp),
                    color = recruit_city
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 40.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    MakeRectangular()
                    TransparentHintTextField(
                        text = diseasesState.text,
                        hint = diseasesState.hint,
                        modifier = Modifier
                            .padding(8.dp)
                            .focusRequester(focusRequester = focusRequester),

                        onValueChange = {
                            viewModel.onEvent(AddEditPatientEvent.EnteredDiseases(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditPatientEvent.ChangeDiseasesFocus(it))
                        },
                        isHintVisible = diseasesState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "기타사항",
                    modifier = Modifier
                        .padding(start = 12.dp, top = 17.dp),
                    color = recruit_city
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp)
                        .padding(end = 40.dp)
                        .padding(top = 10.dp)
                        .padding(bottom = 20.dp)
                ) {
                    val cornerRadius: Dp = 10.dp
                    val cutCornerSize: Dp = 0.dp
                    Canvas(
                        modifier = Modifier
                            .matchParentSize()
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
                    TransparentHintTextField(
                        text = extraState.text,
                        hint = extraState.hint,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                            .focusRequester(focusRequester = focusRequester),

                        onValueChange = {
                            viewModel.onEvent(AddEditPatientEvent.EnteredExtra(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditPatientEvent.ChangeExtraFocus(it))
                        },
                        isHintVisible = extraState.isHintVisible,

                        textStyle = MaterialTheme.typography.body1
                    )
                }
            }

        }


        }
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}