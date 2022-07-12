package com.example.i_go.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.i_go.feature_note.domain.model.Patient

@Database(
    entities = [Patient::class],
    version = 1
)
abstract class PatientDatabase: RoomDatabase() {
    abstract val patientDao: PatientDao

    companion object {
        const val DATABASE_NAME = "patients_db"
    }
}