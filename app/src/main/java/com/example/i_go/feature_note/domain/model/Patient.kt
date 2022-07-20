package com.example.i_go.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.i_go.R

@Entity
data class Patient(
    val name: String,
    val sex: String,
    val age: String,
    val blood_type: String,
    val blood_rh: String,
    val disease: String,
    val extra: String,
    val timestamp: Long,
    val image: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val patientImages = listOf(0,1,2,3,4)
        val patient_image_real = listOf(R.drawable.old_woman, R.drawable.old_man,
        R.drawable.multivitamin, R.drawable.wheelchair, R.drawable.medical_bed)
    }
}

class InvalidNoteException(message: String): Exception(message)