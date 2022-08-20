package com.example.i_go.feature_note.data.remote.responseDTO

import com.example.i_go.R

data class PatientDTO(
    val name: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val blood_type: Int? = null,
    val blood_rh: Boolean? = null,
    val disease: String? = null,
    val extra: String? = null,
    val image: Int? = null,
) {
    companion object {
        val patientImages = listOf(0,1,2,3,4)
        val patient_image_real = listOf(
            R.drawable.old_woman, R.drawable.old_man,
            R.drawable.multivitamin, R.drawable.wheelchair, R.drawable.medical_bed)
    }
}