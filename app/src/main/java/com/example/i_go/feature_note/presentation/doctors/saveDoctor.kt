package com.example.i_go.feature_note.presentation.doctors

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.i_go.facility_dataStore
import com.example.i_go.major_dataStore
import com.example.i_go.name_dataStore

suspend fun saveDoctorName(context: Context, nameValue: String) {
    val nameKey = stringPreferencesKey("doctor_name")

    context.name_dataStore.edit{
        it[nameKey] = nameValue
    }
}

suspend fun saveDoctorMajor(context: Context, majorValue: String) {
    val majorKey = stringPreferencesKey("doctor_major")

    context.major_dataStore.edit{
        it[majorKey] = majorValue
    }
}

suspend fun saveDoctorFacility(context: Context, facilityValue: String) {
    val facilityKey = stringPreferencesKey("doctor_facility")

    context.facility_dataStore.edit{
        it[facilityKey] = facilityValue
    }
}